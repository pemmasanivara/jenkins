package com.llk.therapist.util;

public class Constants {
	private Constants() {};
	public static final String DATE_FORMAT=com.llk.common.util.Constants.DATE_FORMAT;
	public static final String TIME_FORMAT=com.llk.common.util.Constants.TIME_FORMAT;
	public static final String SQL_DATE_FORMAT="yyyy-MM-dd";
	public static final String SQL_TIME_FORMAT="HH:mm:ss";
	public static final String API_VERSION = "/api/v1";
	public static final String IMG_UPLOAD_PATH = "/profile/image/";
	public static final String EX_MSG_USER_REGISTER = "Unable to register.Please try agin.";
	public static final String STATUS_SUCCESS = "SUCCESS";
	public static final String USER_NAME_VALID = "Valid Username";
	public static final String USER_NAME_EXIST = "Username already exist.Please choose another.";
	public static final String INVALID_USER_NAME = "Invalid Username.";
	public static final String INVALID_OLD_PASSWORD = "Invalid old password.";
	public static final String PASSWORD_REST_SUCCESS = "Password rest is successful";
	public static final String THERAPIST_DEL_SUCCESS_MSG = "Therapist deleted successfully.";
	public static final String THERAPIST_DEL_FAILURE_MSG = "Unable to delete therapist";

	public static final String MSG_SUCCESS = "Success";
	public static final String MSG_FAILURE = "Failure";
	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_BOOKED = "BOOKED";
	public static final String STATUS_OPEN = "OPEN";
	public static final String STATUS_CANCELLED = "CANCELLED";
	public static final String PROFILE_DEV="dev";
	public static final String SUCCESS_STATUS="Success";
	
	public static final String FREQUENCY_DAILY="daily";
	public static final String FREQUENCY_WEEKLY="Weekly";
	public static final String FREQUENCY_MONTHLY="monthly";
	
	public static final String EVERY_FIRST="first";
	public static final String EVERY_SECOND="second";
	public static final String EVERY_THIRD="third";
	public static final String EVERY_FOURTH="fourth";
	public static final String EVERY_LAST="last";
	public static final String DAY="day";
	public static final String WEEKDAY="weekday";
	
	
	

	public static final String URL_SAVE_THERAPIST = "/save";
	public static final String URL_SEARCH_THERAPISTS = "/search";
	public static final String URL_DELETE_THERAPIST = "/delete/{therapistId}";
	public static final String URL_THERAPIST_DETAILS = "/detail/{therapistId}";
	public static final String URL_UPDATE_THERAPIST = "/update";
	public static final String URL_THERAPISTS_ALL = "/all";
	public static final String URL_THERAPY_TYPES = "/therapytypes";
	public static final String URL_THERAPIST_LEVELS = "/levels";
	public static final String URL_COMMUNICATION_MODES = "/communicationmodes";
	public static final String URL_THERAPY_AVAILABILITIES="/therapyavailabilities";
	public static final String URL_LOV="/lov/{type}";
	public static final String URL_UPDATE_THERAPIST_SCHEDULE="/updateschedule";
	public static final String URL_CANCEL_THERAPIST_SCHEDULE="/cancelschedule";	

	public static final String SQL_COMMUNICATION_MODES = "select communication_mode_id as value,mode_name as display from COMMUNICATION_MODES";
	public static final String SQL_THERAPY_TYPES = "select THERAPY_ID as value,THERAPY_NAME as display from THERAPY_TYPES order by THERAPY_NAME";
	public static final String SQL_PARTY_ID = "SELECT PARTY_ID FROM THERAPIST where THERAPIST_ID=:THERAPIST_ID";
	public static final String SQL_THERAPIST_LEVELS = "select level_id,level_name from THERAPIST_LEVEL order by level_name";

	public static final String SQL_CREATE_PARTY = "INSERT INTO PARTY(FIRST_NAME,LAST_NAME,EMAIL,PHONE,GENDER,DOB,PROFILE_IMG_URL) VALUES(?,?,?,?,?,?,?)";
	public static final String SQL_CREATE_THERAPIST = "INSERT INTO THERAPIST(PARTY_ID,LEVEL_ID,STATUS,COMMUNICATION_MODE,BCBA_ID,CERTIFICATAIONS,ATTRIBUTE1) VALUES(?,?,?,?,?,?,?)";
	public static final String SQL_SEARCH_THERAPIST = "select DISTINCT first_name ,last_name,phone,gender,email,therapist_id,level_id,PROFILE_IMG_URL from THERAPISTS_ALL_VIEW where 1=1 AND THERAPIST_STATUS='ACTIVE'";
	public static final String SQL_SAVE_THERAPIST_SESSION = "insert into THERAPIST_SESSIONS(THERAPIST_ID,THERAPY_ID) values(:THERAPIST_ID,:THERAPY_ID)";
	public static final String SQL_DELETE_THERAPIST = "update THERAPIST set status='INACTIVE' where therapist_id=:therapist_id";
	public static final String SQL_CREATE_THERAPIST_SCHEDULE = "INSERT INTO THERAPIST_SCHEDULES(THERAPIST_ID,THERAPY_ID,START_DATE,START_TIME,END_DATE,END_TIME,STATUS,FREQUENCE_TYPE,EVERY_DAY,EVERY_WEEK,EVERY_MONTH,WEEK_DAYS,DAY,EVERY)"
			+ " values(:THERAPIST_ID,:THERAPY_ID,:START_DATE,:START_TIME,:END_DATE,:END_TIME,:STATUS,:FREQUENCE_TYPE,:EVERY_DAY,:EVERY_WEEK,:EVERY_MONTH,:WEEK_DAYS,:DAY,:EVERY)";
	public static final String SQL_CREATE_THERAPIST_SCHEDULE_OCCURENCE = "INSERT INTO THERAPIST_SCHEDULE_OCCURENCES(SCHEDULE_ID,FREQUENCE_TYPE,EVERY_DAY,EVERY_WEEK,EVERY_MONTH,WEEK_DAYS,DAY,EVERY,STATUS)"
			+ " VALUES (:SCHEDULE_ID,:FREQUENCE_TYPE,:EVERY_DAY,:EVERY_WEEK,:EVERY_MONTH,:WEEK_DAYS,:DAY,:EVERY,:STATUS)";

	public static final String SQL_THERAPIST_DETAILS = "SELECT DISTINCT FIRST_NAME,LAST_NAME,EMAIL,PHONE,DOB,THERAPIST_ID,PROFILE_IMG_URL,CERTIFICATAIONS,GENDER,LEVEL_ID,LEVEL_NAME,COMMUNICATION_MODE,ATTRIBUTE1 FROM THERAPISTS_ALL_VIEW WHERE THERAPIST_ID=:THERAPIST_ID";
	public static final String SQL_THERAPISTS_ALL = "select distinct NAME,THERAPIST_ID from THERAPISTS_ALL_VIEW WHERE THERAPIST_STATUS='ACTIVE'";
	public static final String SQL_UPDATE_PARTY = "UPDATE PARTY SET FIRST_NAME:=FIRST_NAME,LAST_NAME=:LAST_NAME,DOB=:DOB,EMAIL=:EMAIL,PHONE=:PHONE WHERE PARTY_ID=:PARTY_ID";
	public static final String SQL_UPDATE_THERAPIST = "UPDATE THERAPIST SET COMMUNICATION_MODE=:COMMUNICATION_MODE,ATTRIBUTE1=:ATTRIBUTE1,CERTIFICATAIONS=:CERTIFICATAIONS,LEVEL_ID=:LEVEL_ID WHERE THERAPIST_ID=:THERAPIST_ID";
	public static final String SQL_VALIDATE_THERAPIST_AVAILABILITY = "SELECT COUNT(*) COUNT FROM THERAPISTS_ALL_VIEW WHERE THERAPIST_ID=:THERAPIST_ID AND START_DATE=:START_DATE  AND END_DATE=:END_DATE AND START_TIME=:START_TIME  AND END_TIME=:END_TIME ";
	public static final String SQL_AVAIABLE_SESSIONS = "select distinct THERAPY_ID,THERAPY_NAME from THERAPISTS_ALL_VIEW";
	public static final String SQL_THERAPY_EVENTS="select schedule_id,therapy_id,therapy_name,therapist_id,name,start_date,start_time,end_date,end_time,status from THERAPISTS_ALL_VIEW where 1=1 and status!='INACTIVE'";
	
	public static final String SQL_LOV="SELECT MEANING as value,DESCRIPTION as display from LLK_LOOKUP_VALUES where LOOKUP_TYPE=:LOOKUP_TYPE";
	
	public static final String SQL_UPDATE_THERAPIST_SCHEDULE="UPDATE THERAPIST_SCHEDULES SET START_DATE=:START_DATE ,START_TIME=:START_TIME,END_DATE=:END_DATE,END_TIME=:END_TIME WHERE THERAPIST_ID=:THERAPIST_ID AND SCHEDULE_ID=:SCHEDULE_ID AND THERAPY_ID=:THERAPY_ID";
	
	public static final String SQL_SCHEDULED_EVENTS="SELECT THERAPIST_ID,THERAPIST_NAME,THERAPY_ID,THERAPIST_SCHEDULE_ID,THERAPY_NAME,CLIENT_ID,CLIENT_SCHEDULE_ID,CLIENT_NAME,CLIENT_EMAIL,START_DATE,START_TIME,END_DATE,END_TIME,STATUS FROM MASTER_SCHEDULES_ALL_VIEW WHERE THERAPIST_SCHEDULE_ID IN(:THERAPIST_SCHEDULE_ID) AND STATUS!='CANCELLED'";	
	
	public static final String SQL_CANCEL_SCHEDULE="INSERT INTO THERAPIST_CANCEL_SCHEDULES(THERAPIST_ID,THERAPIST_SCHEDULE_ID,THERAPY_ID,CLIENT_ID,CLIENT_SCHEDULE_ID,START_DATE,START_TIME,END_DATE,END_TIME) VALUES(:THERAPIST_ID,:THERAPIST_SCHEDULE_ID,:THERAPY_ID,:CLIENT_ID,:CLIENT_SCHEDULE_ID,:START_DATE,:START_TIME,:END_DATE,:END_TIME)";

	public static final String SQL_CANCEL_SCHEDULES="SELECT ID,THERAPIST_ID,THERAPIST_SCHEDULE_ID,THERAPY_ID,CLIENT_ID,CLIENT_SCHEDULE_ID,START_DATE,START_TIME,END_DATE,END_TIME FROM THERAPIST_CANCEL_SCHEDULES WHERE THERAPIST_SCHEDULE_ID IN(:THERAPIST_SCHEDULE_ID)";
	
	public static final String SQL_INACTIVE_ALL_SCHEDULES="UPDATE THERAPIST_SCHEDULES SET STATUS='INACTIVE' WHERE THERAPIST_ID=:THERAPIST_ID";
	
	public static final String SQL_UPDATE_STATUS_SCHEDULES="UPDATE THERAPIST_SCHEDULES SET STATUS=:STATUS WHERE THERAPIST_ID=:THERAPIST_ID AND SCHEDULE_ID=:SCHEDULE_ID";
	

}
