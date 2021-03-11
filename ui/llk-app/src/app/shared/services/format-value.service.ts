/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FormatValueService {
  public therapistSaveBaseObject() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      gender: '',
      certifications: '',
      dob: '',
      therapyId: null,
      therapyName: null,
      communicationMode: [],
      levelId: null,
      schedule: {
        startDate: '',
        startTime: '',
        endDate: '',
        endTime: '',
        scheduleOccurence: {
          endDate: '',
          frequence: null,
          weekDays: ''
        }
      }
    }
  }

  public therapistUpdateBaseObject() {
    return {
      partyId: 0,
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      gender: '',
      therapistId: null,
      levelId: null,
      communicationMode: [],
      certifications: '',
      therapyId: null,
      therapyName: null,
      schedule: {
        startDate: '',
        startTime: '',
        endDate: '',
        endTime: '',
        scheduleOccurence: {
          frequence: null,
          weekDays: '',
          endDate: ''
        }
      }
    }
  }

  public clientSaveBaseObject() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      gender: '',
      dob: '',
      guardianName: '',
      guardianRelation: '',
      addresses: [],
      interestedClasses: '',
      insuranceDetails: '',
      sendNotification: 'Y',
      communicationMode: [],
      levelId: null,
      schedule: {
        status: 'ACTIVE',
        therapyId: null,
        therapyName: null,
        startDate: '',
        startTime: '',
        endDate: '',
        endTime: '',
        scheduleOccurence: {
          frequencyType: 'Weekly',
          frequencyRepeats: 3,
          endDate: '',
          frequence: null,
          weekDays: '',
          status: 'ACTIVE'
        }
      }
    }
  }

  public clientUpdateBaseObject() {
    return {
      partyId: 0,
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      gender: '',
      dob: '',
      clientId: null,
      levelId: null,
      communicationMode: [],
      guardianName: '',
      guardianRelation: '',
      addresses: [],
      interestedClasses: '',
      insuranceDetails: '',
      sendNotification: 'Y',
      schedule: {
        status: 'ACTIVE',
        therapyId: null,
        therapyName: null,
        startDate: '',
        startTime: '',
        endDate: '',
        endTime: '',
        scheduleOccurence: {
          frequencyType: 'Weekly',
          frequencyRepeats: 3,
          frequence: null,
          weekDays: '',
          endDate: '',
          status: 'ACTIVE'
        }
      }
    }
  }

  public therapistEventBaseObject() {
    return {
      therapistId: null,
      therapyId: null,
      schedule: {
        scheduleId: null,
        startDate: null,
        startTime: null,
        endDate: null,
        endTime: null
      }
    }
  }

  public formatAddTherapist(currentObject: any) {
    let baseObject = this.therapistSaveBaseObject();
    let tempObject = Object.assign({}, baseObject);
    tempObject = this.formatMatchingFields(tempObject, currentObject);
    tempObject.therapyId = currentObject.sessionsCovered.therapyId;
    tempObject.therapyName = currentObject.sessionsCovered.therapyName;
    tempObject.levelId = currentObject.therapistType;
    tempObject.certifications = currentObject.certifications;
    tempObject.communicationMode = currentObject.communicationMode;
    tempObject = this.formatTherapistSchedule(tempObject, currentObject);
    return tempObject;
  }

  public formatMatchingFields(tempObject: any, currentObject: any) {
    tempObject.firstName = currentObject.firstName;
    tempObject.lastName = currentObject.lastName;
    tempObject.email = currentObject.email;
    tempObject.phone = currentObject.phone;
    tempObject.gender = currentObject.gender;
    tempObject.dob = currentObject.dob ? this.formatDate(currentObject.dob) : null;
    return tempObject;
  }

  public formatTherapistSchedule(tempObject: any, currentObject: any) {
    tempObject.schedule.startDate = currentObject.startDate ? this.formatDate(currentObject.startDate) : null;
    tempObject.schedule.startTime = currentObject.startTime;
    tempObject.schedule.endDate = currentObject.endDate ? this.formatDate(currentObject.endDate) : null;
    tempObject.schedule.endTime = currentObject.endTime;
    tempObject.schedule.scheduleOccurence.endDate = currentObject.endDate ? this.formatDate(currentObject.endDate) : null;
    tempObject.schedule.scheduleOccurence.frequence = currentObject.repeats;
    tempObject.schedule.scheduleOccurence.weekDays = this.formatDateString(currentObject.repeatsOn);
    return tempObject;
  }

  public formatUpdateTherapist(currentObject: any) {
    let baseObject = this.therapistUpdateBaseObject();
    let tempObject = Object.assign({}, baseObject);
    tempObject = this.formatMatchingFields(tempObject, currentObject);
    tempObject.therapistId = currentObject.therapistId;
    tempObject.therapyId = currentObject.sessionsCovered.therapyId;
    tempObject.therapyName = currentObject.sessionsCovered.therapyName;
    tempObject.levelId = currentObject.therapistType;
    tempObject.certifications = currentObject.certifications;
    tempObject.communicationMode = currentObject.communicationMode;
    tempObject = this.formatTherapistSchedule(tempObject, currentObject);
    return tempObject;
  }

  public formatAddClient(currentObject: any) {
    let baseObject = this.clientSaveBaseObject();
    let tempObject = Object.assign({}, baseObject);
    tempObject = this.formatMatchingFields(tempObject, currentObject);
    tempObject.communicationMode = currentObject.communicationMode;
    tempObject.guardianRelation = currentObject.guardianRelation;
    tempObject.guardianName = currentObject.guardianName;
    tempObject.guardianRelation = tempObject.guardianRelation;
    tempObject.interestedClasses = currentObject.interestedClasses;
    tempObject.addresses = [];
    tempObject.insuranceDetails = currentObject.insuranceDetails;
    tempObject = this.formatClientSchedule(tempObject, currentObject);
    return tempObject;
  }

  public formatClientSchedule(tempObject: any, currentObject: any) {
    tempObject.schedule.therapyId = currentObject.sessionsCovered.therapyId;
    tempObject.schedule.therapyName = currentObject.sessionsCovered.therapyName;
    tempObject.schedule.startDate = currentObject.startDate ? this.formatDate(currentObject.startDate) : null;
    tempObject.schedule.startTime = currentObject.startTime;
    tempObject.schedule.endDate = currentObject.endDate ? this.formatDate(currentObject.endDate) : null;
    tempObject.schedule.endTime = currentObject.endTime;
    tempObject.schedule.scheduleOccurence.endDate = currentObject.endDate ? this.formatDate(currentObject.endDate) : null;
    tempObject.schedule.scheduleOccurence.frequence = currentObject.repeats;
    tempObject.schedule.scheduleOccurence.weekDays = this.formatDateString(currentObject.repeatsOn);
    return tempObject;
  }

  public formatUpdateClient(currentObject: any) {
    let baseObject = this.clientUpdateBaseObject();
    let tempObject = Object.assign({}, baseObject);
    tempObject = this.formatMatchingFields(tempObject, currentObject);
    tempObject.clientId = currentObject.clientId;
    tempObject.guardianName = currentObject.guardianName;
    tempObject.guardianRelation = tempObject.guardianRelation;
    tempObject.interestedClasses = currentObject.interestedClasses;
    tempObject.insuranceDetails = currentObject.insuranceDetails;
    tempObject.addresses = [];
    tempObject.communicationMode = currentObject.communicationMode;
    tempObject.guardianRelation = currentObject.guardianRelation;
    tempObject = this.formatClientSchedule(tempObject, currentObject);
    return tempObject;
  }

  public formatUpdateTherapistEvent(historyObject: any, currentObject: any) {
    let baseObject = this.therapistEventBaseObject();
    let tempObject = Object.assign({}, baseObject);
    tempObject.therapistId = historyObject.therapistId;
    tempObject.therapyId = historyObject.therapyId;
    tempObject.schedule.scheduleId = currentObject.id;
    tempObject.schedule.startDate = currentObject.start ? this.formatDate(currentObject.start) : null;
    tempObject.schedule.startTime = currentObject.start ? this.formatTime(currentObject.start) : null;;
    tempObject.schedule.endDate = currentObject.end ? this.formatDate(currentObject.end) : null;
    tempObject.schedule.endTime = currentObject.end ? this.formatTime(currentObject.end) : null;;
    return tempObject;
  }

  public formatTime(date) {
    var newDate = new Date(date.getTime() + (date.getTimezoneOffset() - 60) * 60 * 1000);
    var formatedTime = new Date(newDate),
      hrs = ("0" + (formatedTime.getHours() + 1)).slice(-2),
      mn = ("0" + formatedTime.getMinutes()).slice(-2);
    return [hrs, mn].join(":");
  }

  public constructSearchUrl(url: any, text: any, start: any, limit: any) {
    let param = null;
    if (text) {
      param = "?srchTxt=" + text;
    }
    if (param) {
      param = param + "&start=" + start + "&limit=" + limit;
    } else {
      param = "?start=" + start + "&limit=" + limit;
    }
    url = url + param;
    return url;
  }

  public constructFiltersUrl(url: any, param: any) {
    let queryParam = [];
    if (param.therapistId) {
      queryParam.push("therapistId=" + param.therapistId);
    }
    if (param.clientId) {
      queryParam.push("clientId=" + param.clientId);
    }
    if (param.sessionsCovered) {
      queryParam.push("therapyId=" + param.sessionsCovered.therapyId);
    }
    if (param.startDate) {
      queryParam.push("startDate=" + this.formatDate(param.startDate));
    }
    if (param.startDate) {
      queryParam.push("endDate=" + this.formatDate(param.endDate));
    }
    if (param.startDate) {
      queryParam.push("startTime=" + param.startTime);
    }
    if (param.startDate) {
      queryParam.push("endTime=" + param.endTime);
    }
    if (param.scheduleId) {
      queryParam.push("scheduleId=" + param.scheduleId);
    }
    return url + "?" + queryParam.join('&');
  }

  public constructCancelTherapistEventUrl(url: any, param: any) {
    let queryParam = [];
    if (param.therapistId) {
      queryParam.push("therapistId=" + param.therapistId);
    }
    if (param.therapistScheduleId) {
      queryParam.push("scheduleId=" + param.therapistScheduleId);
    }
    if (param.therapyId) {
      queryParam.push("therapyId=" + param.therapyId);
    }
    return url + "?" + queryParam.join('&');
  }

  public formatDate(str) {
    var date = new Date(str),
      mnth = ("0" + (date.getMonth() + 1)).slice(-2),
      day = ("0" + date.getDate()).slice(-2);
    return [mnth, day, date.getFullYear()].join("/");
  }

  public formatDateString(dateObject) {
    let selectedDate = '';
    Object.keys(dateObject).forEach(function (key) {
      if (dateObject[key]) {
        if (selectedDate !== '') {
          selectedDate = selectedDate + ',' + key;
        } else {
          selectedDate = selectedDate + key;
        }
      }
    });
    return selectedDate;
  }

  public setFilteredValues(userData, filterData) {
    let tempUserData = userData && userData.data ? userData.data : {};
    let tempFilterdata = filterData ? filterData : tempUserData;
    if (filterData && filterData.client) {
      tempFilterdata["clientId"] = filterData.client.clientId;
    }
    if (filterData && filterData.sessionsCovered) {
      tempFilterdata["sessionsCovered"] = filterData.sessionsCovered;
    }
    if (filterData && filterData.therapist) {
      tempFilterdata["therapistId"] = filterData.therapist.therapistId;
    }
    return tempFilterdata;
  }

  public deltaDate(input, days, months, years) {
    return new Date(
      input.getFullYear() + years,
      input.getMonth() + months,
      Math.min(
        input.getDate() + days,
        new Date(input.getFullYear() + years, input.getMonth() + months + 1, 0).getDate()
      )
    );
  }

}
