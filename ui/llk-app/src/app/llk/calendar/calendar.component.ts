/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef, ViewChild, ViewChildren, ElementRef, QueryList } from '@angular/core';
import { OptionsInput } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import ListView from '@fullcalendar/list';
import { FullCalendarComponent } from '@fullcalendar/angular';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { LLKHttpService, msgResource, UtilService, InitValueService, FormatValueService } from '../../shared';
import moment from 'moment';
import { ActivatedRoute } from '@angular/router'
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EventBookingComponent } from '../../shared/';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {

  calendarOptions: OptionsInput;
  eventsModel: any;
  @ViewChild('fullcalendar') fullcalendar: FullCalendarComponent;
  @ViewChildren('fullcalendar') cal: QueryList<ElementRef>;

  ngAfterViewInit() {

  }

  breakpoint;
  formGroup: FormGroup;
  post: any = '';
  state: Observable<object>;

  onResize(event) {
    this.breakpoint = (event.target.innerWidth <= 567) ? 1 : 2;
  }

  constructor(private utilService: UtilService, private snackBar: MatSnackBar, public dialog: MatDialog, private activatedRoute: ActivatedRoute, private formatValueService: FormatValueService, private initValueService: InitValueService, private cdr: ChangeDetectorRef, private llkHttpService: LLKHttpService, private changeDetectorRef: ChangeDetectorRef, private formBuilder: FormBuilder) {

  }

  public msgConstants: any = msgResource;
  public configAppUrls: any = msgResource.llkPortal.appUrls;
  public configPatchUrls: any = msgResource.llkPortal.appUrls.patchUrls;
  public visible = false;
  public eventsList = [];
  public searchEventsList;
  public therapyTypesList;
  public clientsList;
  public therapistsList;
  public historyState;
  public selectedTherapyTypes;
  public selectedClients;
  public selectedTherapists;
  public eventsBackList = [];
  public currentFormData;

  ngOnInit() {
    this.therapyTypes();
    this.getTherapists();
    this.getClients();
    this.activatedRoute.params.subscribe(params => {
      this.historyState = window.history.state;
      if (this.historyState.page === "therapist") {
        this.searchEventsService(this.historyState.data, "therapist");
      } else if (this.historyState.page === "client") {
        this.searchEventsService(this.historyState.data, "client");
      } else {
        this.searchEventsService({}, "calander");
      }
    });

    this.createForm();
    this.breakpoint = (window.innerWidth <= 567) ? 1 : 2;
    this.calendarOptions = {
      timeZone: "UTC",
      plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin, ListView, interactionPlugin],
      editable: false,
      customButtons: {
        myCustomButton: {
          text: 'custom!',
          click: function () {
            alert('clicked the custom button!');
          }
        }
      },
      timeFormat: 'H(:mm)',
      headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
      },
      navLinks: !0,
      eventLimit: false,
      forceEventDuration: true,
      startEditable: true,
      durationEditable: true,
      events: this.eventsList,
      eventAllow: function (dropInfo, draggedEvent) {
        if (draggedEvent.id === '999') {
          return dropInfo.start < new Date(2016, 0, 1); // a boolean
        }
        else {
          return true;
        }
      },
      dateClick: this.handleDateClick.bind(this),
      eventClick: this.handleEventClick.bind(this),
      eventResize: this.handleEventResize.bind(this)
    };
  }

  public updateCalendar() {
    this.calendarOptions.editable = true;
    this.calendarOptions.eventDragStop = this.handleEventDragStop.bind(this);
    this.calendarOptions.eventDrop = this.handleEventDrop.bind(this);
  }
  public handleEventDragStop(arg) {
    let selectedEvent = this.filterEvent(arg);
    let dataFlag = this.validateUpdateTherapistEvent(arg.event);
    return dataFlag;
  }
  public openDialog(event) {
    const dialogRef = this.dialog.open(EventBookingComponent, {
      width: '500px',
      data: event
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result && result.page === "client") {
        result["clientId"] = this.historyState.data.clientId;
        this.bookEvent(result);
      } else if (result && result.page === "therapist") {
        this.cancleEvent(result);
      }
    });
  }

  public cancleEvent(param) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.cancelTherapistEvent;
    url = this.formatValueService.constructCancelTherapistEventUrl(url, param);
    this.callDeleteService(url, 'clearTherapistEvent');
  }

  public therapyTypes() {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.therapyTypes;
    this.callGetService(url, 'therapyTypesList');
  }

  public getTherapists() {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.therapists;
    this.callGetService(url, 'therapistsList');
  }

  public getClients() {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.clients;
    this.callGetService(url, 'clientsList');
  }

  public searchEventsService(param: any, page: any) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.therapyavailabilities;
    url = this.formatValueService.constructFiltersUrl(url, param);
    if (page === "therapist" || page === "client") {
      this.visible = false;
    } else {
      this.visible = true;
    }
    this.callGetService(url, 'searchEventsList');
  }

  public callDeleteService(url: any, list: any) {
    this.llkHttpService.deleteService(url).subscribe(
      (data: any) => {
        this.setData(data, list);
      });
  }

  public callGetService(url: any, list: any) {
    this.llkHttpService.getService(url).subscribe(
      (data: any) => {
        this.setData(data, list);
      });
  }

  public handleChange() {
    let param = this.formatValueService.setFilteredValues(this.historyState, this.formGroup.value);
    let page = this.historyState ? this.historyState.page : "calander";
    this.searchEventsService(param, page);
  }

  public handleEventResize(arg) {
    let selectedEvent = this.filterEvent(arg);
    let data = this.formatValueService.formatUpdateTherapistEvent(selectedEvent[0], arg.event);
    this.updateTherapistEvent(data);

    console.log("resize" + arg);
  }



  public validateUpdateTherapistEvent(arg) {
    let today = new Date();
    let eventDate = new Date(arg.start);
    return today.getTime() > eventDate.getTime();

  }
  public handleEventDrop(arg) {
    let selectedEvent = this.filterEvent(arg);
    let data = this.formatValueService.formatUpdateTherapistEvent(selectedEvent[0], arg.event);
    this.updateTherapistEvent(data);
    console.log("Drag" + arg);
  }


  public updateTherapistEvent(data) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.updateTherapistEvent;
    this.llkHttpService.putService(data, url).subscribe(
      (data: any) => {
        this.openSnackBar("Successfully updated", "");
      });
  }

  public setData(data: any, list: any) {
    switch (list) {
      case "therapyTypesList":
        if (data) {
          this.therapyTypesList = data;
        }
        break;
      case "therapistsList":
        if (data) {
          this.therapistsList = data;
        }
        break;
      case "clientsList":
        if (data) {
          this.clientsList = data;
        }
        break;
      case "searchEventsList":
        if (data) {
          this.eventsBackList = data;
          this.formatData(data);
        }
        break;
      case "clearTherapistEvent":
        if (data) {
          this.searchEventsService(this.historyState.data, "therapist");
        }
        break;
      default:
        break;
    }
  }

  // repeatsOn;
  public createForm() {
    this.formGroup = this.formBuilder.group({
      'sessionsCovered': [null],
      'therapist': [null],
      'client': [null]
    });
  }

  public handleDateClick(arg) {
    console.log(arg);

  }

  public handleEventClick(arg) {
    let selectedEvent = this.filterEvent(arg);
    if (this.historyState.data.clientId) {
      selectedEvent[0].page = "client"
      this.openDialog(selectedEvent[0]);
    }
    if (this.historyState.data.therapistId) {
      selectedEvent[0].page = "therapist"
      this.openDialog(selectedEvent[0]);
    }
  }

  public bookEvent(post) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.eventBooking;
    this.llkHttpService.postService(post, url).subscribe(
      (data: any) => {
        this.handleAfterSave();
      });
  }

  public filterEvent(result) {
    var selectedEvent = this.eventsBackList.filter(function (event) {
      return event.groupId == result.event.groupId;
    });
    return selectedEvent;
  }

  handleAfterSave() {
    this.openSnackBar("Successfully Booked", "");
    this.searchEventsService(this.historyState.data, "client");

  }
  public openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  public updateHeader() {
    this.calendarOptions.headerToolbar = {
      left: 'prev,next myCustomButton',
      center: 'title',
      right: ''
    };
  }

  public updateEvents() {
    const nowDate = new Date();
    const yearMonth = nowDate.getUTCFullYear() + '-' + (nowDate.getUTCMonth() + 1);
    this.calendarOptions.events = [{
      title: 'Updaten Event',
      start: yearMonth + '-08',
      end: yearMonth + '-10'
    }];
  }

  public onSubmit(post) {
    this.post = post;
  }

  public formatData(data: any) {
    let newList = [];
    data.forEach(function (value) {
      let dateObject = {
        title: '',
        id: '',
        groupId: '',
        start: null,
        end: null,
        className: '',
        slotDuration: null
      }
      let startDate = value.startDate.replace(/(\d\d)\/(\d\d)\/(\d{4})/, "$3-$1-$2");
      let endDate = value.startDate.replace(/(\d\d)\/(\d\d)\/(\d{4})/, "$3-$1-$2");
      dateObject.title = value.title;
      dateObject.id = value.therapistScheduleId;
      dateObject.groupId = value.groupId;
      dateObject.start = startDate + "T" + value.startTime + ":00+00:00";
      dateObject.end = endDate + "T" + value.endTime + ":00+00:00";
      switch (value.therapyId) {
        case 1:
          dateObject.className = 'bg-speech';
          break;
        case 2:
          dateObject.className = 'bg-art';
          break;
        case 3:
          dateObject.className = 'bg-occupational';
          break;
        case 4:
          dateObject.className = 'bg-behavior';
          break;
        case 5:
          dateObject.className = 'bg-music';
          break;
        case 6:
          dateObject.className = 'bg-recreational';
          break;
        case 7:
          dateObject.className = 'bg-technology';
          break;
        case 8:
          dateObject.className = 'bg-life-skills';
          break;
        case 9:
          dateObject.className = 'bg-social';
          break;
        default:
          break;
      }
      if ((value.scheduledEvents && value.scheduledEvents.length > 0)) {
        dateObject.className = dateObject.className + ' disabled-event';
      }
      if (value.status && value.status === "CANCELLED") {
        dateObject.className = dateObject.className + ' canceled-event';
      }
      newList.push(dateObject);
    });
    this.calendarOptions.events = newList;
    if (this.historyState.page === "therapist") {
      this.updateCalendar();
    }
  }

}
