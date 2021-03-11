package com.llk.therapist.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.llk.therapist.dao.CalendarDAOImpl;
import com.llk.therapist.model.Therapist;
import com.llk.therapist.model.TherapyEvent;
/**
 * 
 * @author Varaprasad.P
 *
 */
public class Util {
	
	private static final Logger logger = LoggerFactory.getLogger(CalendarDAOImpl.class);

	public static String imgDownloadUri(String fileName) {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(Constants.API_VERSION + Constants.IMG_UPLOAD_PATH).path(fileName).toUriString();
		return fileDownloadUri;
	}

	public static String saveProfileImage(MultipartFile profileImg) throws IOException {
		String avatarUrl = "";
		if (profileImg != null) {
			String fileName = com.llk.common.util.Util
					.getImageName(StringUtils.cleanPath(profileImg.getOriginalFilename()));
			Files.copy(profileImg.getInputStream(), Paths.get(Constants.IMG_UPLOAD_PATH + fileName),
					StandardCopyOption.REPLACE_EXISTING);
			avatarUrl = "/therapist" + Constants.API_VERSION + Constants.IMG_UPLOAD_PATH + fileName;
		}
		return avatarUrl;
	}

	public static LocalTime parseTime(String sTime, String format) {
		LocalTime time = null;
		try {
			time = LocalTime.parse(sTime, DateTimeFormatter.ofPattern(format));
		} catch (Exception ex) {
		}
		return time;
	}

	public static LocalDate parseDate(String sdate, String format) {
		LocalDate date = null;
		try {
			date = LocalDate.parse(sdate, DateTimeFormatter.ofPattern(format));
		} catch (Exception ex) {
		}
		return date;
	}

	public static String formatDate(String sdate, String format) {
		String returnDate = null;
		try {
			LocalDate date = LocalDate.parse(sdate, DateTimeFormatter.ofPattern(format));
			DateTimeFormatter formater = DateTimeFormatter.ofPattern(Constants.SQL_DATE_FORMAT);
			returnDate = date.format(formater);
			logger.info("returnDate-->" + returnDate);
			return returnDate;
		} catch (Exception ex) {
			logger.error("Date parse error->", ex);
		}
		return returnDate;
	}

	public static String formatTime(String sTime, String format) {
		logger.info("sTime-->" + sTime);
		logger.info("format-->" + format);
		String returnTime = null;
		try {
			LocalTime time = LocalTime.parse(sTime, DateTimeFormatter.ofPattern(format));
			DateTimeFormatter formater = DateTimeFormatter.ofPattern(Constants.SQL_TIME_FORMAT);
			returnTime = time.format(formater);
			logger.info("returnTime-->" + returnTime);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Date parse error->", ex);
		}
		return returnTime;
	}

	/**
	 * 
	 * @return
	 */
	public static ActiveMQQueue getMqueue() {
		return new ActiveMQQueue(com.llk.common.util.Constants.QUEUE_THERAPIST);
	}

	/**
	 * 
	 * @param therapist
	 * @return
	 */
	public static List<TherapyEvent> getTherapistScheduledEvents(Therapist therapist) {
		List<TherapyEvent> events = new ArrayList<TherapyEvent>();
		LocalDate startDate = therapist.getSchedule().getStartDate();
		LocalDate endDate = therapist.getSchedule().getEndDate();
		long days = ChronoUnit.DAYS.between(startDate, endDate);
		long noweeks = ChronoUnit.WEEKS.between(therapist.getSchedule().getStartDate(),
				therapist.getSchedule().getEndDate());
		logger.info("noweeks-->" + noweeks);
		logger.info("days-->" + days);
		List<LocalDate> daysRange = Stream.iterate(startDate, date -> date.plusDays(1)).limit(days + 1)
				.collect(Collectors.toList());
		logger.info("daysRange-->" + daysRange);
		for (int i = 0; i < daysRange.size(); i++) {
			LocalDate cloneEndDate = daysRange.get(i);
			if (cloneEndDate != null) {
				TherapyEvent event = new TherapyEvent();
				event.setTherapistId(therapist.getTherapistId());
				event.setTherapyId(Integer.valueOf(therapist.getTherapyId()));
				event.setStartDate(cloneEndDate);
				event.setEndDate(cloneEndDate);
				event.setStartTime(therapist.getSchedule().getStartTime());
				event.setEndTime(therapist.getSchedule().getEndTime());
				events.add(event);
			}
		}
		logger.info("events size-->"+events.size());
		// Filter the events based on recurrence
		if (therapist.getSchedule().getScheduleOccurence() != null) {
			String frequencyType = therapist.getSchedule().getScheduleOccurence().getFrequenceType();
			logger.info("frequencyType-->" + frequencyType);
			String weekDays = therapist.getSchedule().getScheduleOccurence().getWeekDays();
			Integer everyDay = therapist.getSchedule().getScheduleOccurence().getEveryDay();			
			if (!isNull(frequencyType) && frequencyType.equalsIgnoreCase(Constants.FREQUENCY_DAILY)) {
				if (!isNull(weekDays)) {
					List<String> ldays = Arrays.asList(weekDays.split(","));
					events = events.stream().filter(event -> {
						String dayName = event.getStartDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
						if (ldays.indexOf(String.valueOf(dayName.toLowerCase())) == -1) {
							return false;
						}
						return true;
					}).collect(Collectors.toList());
				} else if (everyDay != null && everyDay > 1) {
					events = IntStream.range(0, events.size()).filter(n -> n % (everyDay) == 0).mapToObj(events::get)
							.collect(Collectors.toList());
				}

			} else if (!isNull(frequencyType) && frequencyType.equalsIgnoreCase(Constants.FREQUENCY_WEEKLY)) {
				int everyWeek = therapist.getSchedule().getScheduleOccurence().getEveryWeek();
				logger.info("everyWeek-->" + everyWeek);				
				List<String> ldays = (weekDays == null || weekDays.trim().length() ==0)?null:Arrays.asList(weekDays.trim().split(","));
				if (everyWeek <= 1) {
					logger.info("ldays-->" + ldays);
					if (ldays != null && ldays.size() > 0) {
						events = events.stream().filter(event -> {
							String dayName = event.getStartDate().getDayOfWeek().getDisplayName(TextStyle.FULL,
									Locale.US);
							if (ldays.indexOf(String.valueOf(dayName.toLowerCase())) == -1) {
								return false;
							}
							return true;
						}).collect(Collectors.toList());
					}

				} else {
					logger.info("ldays-->" + ldays);
					LocalDate startDateWeek = getWeekStartDate(therapist.getSchedule().getStartDate());
					LocalDate endDateWeek = getWeekEndDate(therapist.getSchedule().getEndDate());
					logger.info("endDateWeek-->" + endDateWeek);
					long wDays = ChronoUnit.DAYS.between(startDateWeek, endDateWeek);
					List<LocalDate> wDaysRange = Stream.iterate(startDateWeek, date -> date.plusDays(1))
							.limit(wDays + 1).collect(Collectors.toList());
					logger.info("wDaysRange-->" + wDaysRange);
					Map<Integer, List<LocalDate>> weeks = new HashMap<>();
					List<LocalDate> filterDates = new ArrayList<>();
					int j = 0;
					int w = 0;
					List<LocalDate> l = null;
					for (int i = 0; i < wDaysRange.size(); i++) {
						if (j == 7) {
							weeks.put(w, l);
							w++;
							j = 0;
						}
						if (j == 0) {
							l = new ArrayList<>();
							l.add(wDaysRange.get(i));
						} else {
							l.add(wDaysRange.get(i));
						}
						j++;
					}
					weeks.put(w, l);
					
					logger.info("weeks-->"+weeks);                   
					weeks.keySet().forEach(key -> {
						if ((key) % everyWeek == 0) {
							filterDates.addAll(weeks.get(key));
						}
					});
					logger.info("filterDates-->" + filterDates);
					logger.info("ldays-->"+ldays);
					events = events.stream().filter(event -> {
						String dayName = event.getStartDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
						logger.info("dayName-->"+dayName);
						 int index=0;
						 if(ldays!=null && ldays.size() > 0) {
						   index = ldays.indexOf(String.valueOf(dayName.toLowerCase()));
						 }
						 logger.info("index-->"+index);
						LocalDate ed = event.getStartDate();
						boolean include = true;
						if (index == -1) {
							include = false;
						}
						if (include) {
							for (LocalDate fd : filterDates) {
								if (ed.isEqual(fd)) {
									include = true;
									break;
								} else {
									include = false;
								}
							}
							logger.info("include-->" + include);
						}
						return include;
					}).collect(Collectors.toList());

					startDateWeek = null;
					endDateWeek = null;
					wDaysRange = null;
				}

			} else if (!isNull(frequencyType) && frequencyType.equalsIgnoreCase(Constants.FREQUENCY_MONTHLY)) {
				int everyMonth = therapist.getSchedule().getScheduleOccurence().getEveryMonth();
				String every = therapist.getSchedule().getScheduleOccurence().getEvery();
				Integer day = therapist.getSchedule().getScheduleOccurence().getDay();
				logger.info("everyMonth-->"+everyMonth);
				logger.info("every-->"+every);
				logger.info("day-->"+everyMonth);
				if (!isNull(every) && !isNull(weekDays)) {
					if (every.equalsIgnoreCase(Constants.EVERY_FIRST)) {
						events = events.stream().filter(event -> {
							LocalDate eDate = event.getStartDate();
							LocalDate d = null;
							if (weekDays.equalsIgnoreCase(Constants.DAY)) {
								d = getFirstDayOfMonth(eDate);

							} else if (weekDays.equalsIgnoreCase(Constants.WEEKDAY)) {
								d = getFirstWeekDay(eDate);
							} else {
								d = getFirstInMonth(eDate, weekDays);
							}

							if (eDate.isEqual(d) && ((event.getStartDate().getMonthValue() - 1) % everyMonth) == 0) {
								return true;
							}
							return false;
						}).collect(Collectors.toList());
					} else if (every.equalsIgnoreCase(Constants.EVERY_SECOND)) {
						events = events.stream().filter(event -> {
							LocalDate eDate = event.getStartDate();
							LocalDate d = null;
							if (weekDays.equalsIgnoreCase(Constants.DAY)) {
								d = getSecondDayOfMonth(eDate);

							} else if (weekDays.equalsIgnoreCase(Constants.WEEKDAY)) {
								d = getSecondWeekDay(eDate);
							} else {
								d = getSecondInMonth(eDate, weekDays);
							}
							if (eDate.isEqual(d) && ((event.getStartDate().getMonthValue() - 1) % everyMonth) == 0) {
								return true;
							}
							return false;
						}).collect(Collectors.toList());
					} else if (every.equalsIgnoreCase(Constants.EVERY_THIRD)) {
						events = events.stream().filter(event -> {
							LocalDate eDate = event.getStartDate();
							LocalDate d = null;
							if (weekDays.equalsIgnoreCase(Constants.DAY)) {
								d = getThirdDayOfMonth(eDate);

							} else if (weekDays.equalsIgnoreCase(Constants.WEEKDAY)) {
								d = getThirdWeekDay(eDate);
							} else {
								d = getThirdInMonth(eDate, weekDays);
							}
							if (eDate.isEqual(d) && ((event.getStartDate().getMonthValue() - 1) % everyMonth) == 0) {
								return true;
							}
							return false;
						}).collect(Collectors.toList());
					} else if (every.equalsIgnoreCase(Constants.EVERY_FOURTH)) {
						events = events.stream().filter(event -> {
							LocalDate eDate = event.getStartDate();
							LocalDate d = null;
							if (weekDays.equalsIgnoreCase(Constants.DAY)) {
								d = getFourthDayOfMonth(eDate);

							} else if (weekDays.equalsIgnoreCase(Constants.WEEKDAY)) {
								d = getFourthWeekDay(eDate);
							} else {
								d = getFourthInMonth(eDate, weekDays);
							}
							if (eDate.isEqual(d) && ((event.getStartDate().getMonthValue() - 1) % everyMonth) == 0) {
								return true;
							}
							return false;
						}).collect(Collectors.toList());
					} else if (every.equalsIgnoreCase(Constants.EVERY_LAST)) {
						events = events.stream().filter(event -> {
							LocalDate eDate = event.getStartDate();
							LocalDate d = null;
							if (weekDays.equalsIgnoreCase(Constants.DAY)) {
								d = getLastDayOfMonth(eDate);

							} else if (weekDays.equalsIgnoreCase(Constants.WEEKDAY)) {
								d = getLastWeekDay(eDate);
							} else {
								d = getLastInMonth(eDate, weekDays);
							}
							if (eDate.isEqual(d) && ((event.getStartDate().getMonthValue() - 1) % everyMonth) == 0) {
								return true;
							}
							return false;
						}).collect(Collectors.toList());
					}

				} else if (everyMonth <= 1) {
					
					events = events.stream().filter(event -> {
						if (event.getStartDate().getDayOfMonth() == day) {
							return true;
						}
						return false;
					}).collect(Collectors.toList());
				} else {
					events = events.stream().filter(event -> {
						if (((event.getStartDate().getMonthValue() - 1) % everyMonth) == 0
								&& event.getStartDate().getDayOfMonth() == day) {
							return true;
						}
						return false;
					}).collect(Collectors.toList());

				}

			}

		}
		logger.info("events size before returning-->"+events.size());
		return events;
	}

	public static boolean isNull(String value) {
		if (value == null || value.isBlank() || value.isEmpty()) {
			return true;
		}
		return false;
	}

	public static LocalDate getWeekStartDate(LocalDate date) {
		LocalDate startDay = date;
		while (startDay.getDayOfWeek() != DayOfWeek.SUNDAY) {
			startDay = startDay.minusDays(1);
		}
		return startDay;
	}

	public static LocalDate getWeekEndDate(LocalDate date) {
		LocalDate endDay = date;
		while (endDay.getDayOfWeek() != DayOfWeek.SATURDAY) {
			endDay = endDay.plusDays(1);
		}
		return endDay;
	}

	public static LocalDate getFirstInMonth(LocalDate date, String day) {
		LocalDate fDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
		switch (day.toUpperCase()) {
		case "MONDAY":
			fDate = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
			break;
		case "TUESDAY":
			fDate = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.TUESDAY));
			break;
		case "WEDNESDAY":
			fDate = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
			break;
		case "THURSDAY":
			fDate = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.THURSDAY));
			break;
		case "FRIDAY":
			fDate = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
			break;
		case "SATURDAY":
			fDate = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
			break;
		case "SUNDAY":
			fDate = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));
			break;
		default:
			// Nothing
		}
		return fDate;
	}

	public static LocalDate getLastInMonth(LocalDate date, String day) {
		LocalDate fDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
		switch (day.toUpperCase()) {
		case "MONDAY":
			fDate = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
			break;
		case "TUESDAY":
			fDate = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.TUESDAY));
			break;
		case "WEDNESDAY":
			fDate = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY));
			break;
		case "THURSDAY":
			fDate = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
			break;
		case "FRIDAY":
			fDate = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));
			break;
		case "SATURDAY":
			fDate = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.SATURDAY));
			break;
		case "SUNDAY":
			fDate = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));
			break;
		default:
			// Nothing
		}
		return fDate;
	}

	public static LocalDate getSecondInMonth(LocalDate data, String day) {
		LocalDate sDate = null;
		sDate = getFirstInMonth(data, day).plusDays(7);
		return sDate;
	}

	public static LocalDate getThirdInMonth(LocalDate data, String day) {
		LocalDate sDate = null;
		sDate = getSecondInMonth(data, day).plusDays(7);
		return sDate;
	}

	public static LocalDate getFourthInMonth(LocalDate data, String day) {
		LocalDate sDate = null;
		sDate = getThirdInMonth(data, day).plusDays(7);
		return sDate;
	}

	public static LocalDate getFirstDayOfMonth(LocalDate date) {
		LocalDate fDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
		fDate = fDate.with(TemporalAdjusters.firstDayOfMonth());
		return fDate;
	}

	public static LocalDate getSecondDayOfMonth(LocalDate date) {
		return getFirstDayOfMonth(date).plusDays(1);
	}

	public static LocalDate getThirdDayOfMonth(LocalDate date) {
		return getSecondDayOfMonth(date).plusDays(1);
	}

	public static LocalDate getFourthDayOfMonth(LocalDate date) {
		return getThirdDayOfMonth(date).plusDays(1);
	}

	public static LocalDate getLastDayOfMonth(LocalDate date) {
		LocalDate fDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
		fDate = fDate.with(TemporalAdjusters.lastDayOfMonth());
		return fDate;
	}

	public static LocalDate getFirstWeekDay(LocalDate date) {
		LocalDate fDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
		fDate = getFirstDayOfMonth(fDate);
		switch (DayOfWeek.of(fDate.get(ChronoField.DAY_OF_WEEK))) {
		case SATURDAY:
			fDate = fDate.plusDays(2);
			break;
		case SUNDAY:
			fDate = fDate.plusDays(1);
			break;
		default:
			break;
		}
		return fDate;
	}

	public static LocalDate getSecondWeekDay(LocalDate date) {
		return getFirstWeekDay(date).plusDays(1);
	}

	public static LocalDate getThirdWeekDay(LocalDate date) {
		return getSecondWeekDay(date).plusDays(1);
	}

	public static LocalDate getFourthWeekDay(LocalDate date) {
		return getThirdWeekDay(date).plusDays(1);
	}

	public static LocalDate getLastWeekDay(LocalDate date) {
		LocalDate fDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
		fDate = getLastDayOfMonth(fDate);
		switch (DayOfWeek.of(fDate.get(ChronoField.DAY_OF_WEEK))) {
		case SATURDAY:
			fDate = fDate.minusDays(1);
			break;
		case SUNDAY:
			fDate = fDate.minusDays(2);
			break;
		default:
			break;
		}
		return fDate;
	}

}
