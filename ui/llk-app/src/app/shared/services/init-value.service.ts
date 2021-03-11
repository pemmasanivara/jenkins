/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class InitValueService {

  public therapistFormGroupInit() {
    return {
      firstName: '',
      lastName: '',
      gender: '',
      email: '',
      phone: '',
      certifications: '',
      avatarUrl: '',
      therapistType: '',
      sessionsCovered: '',
      repeats: '',
      repeatsEvery: '',
      dob: '',
      therapistId: null,
      communicationMode: [],
      remainder: null,
      repeatsOn: {
        sunday: false,
        monday: false,
        tuesday: false,
        wednesday: false,
        thursday: false,
        friday: false,
        saturday: false,
      },
      startDate: '',
      endDate: '',
      startTime: '',
      endTime: '',
      duration: '',
      validate: true
    }
  }

  public clientFormGroupInit() {
    return {
      firstName: '',
      lastName: '',
      gender: '',
      email: '',
      phone: '',
      addressOne: {},
      addressTwo: {},
      addresses: [],
      profile: [],
      avatarUrl: '',
      sessionsCovered: '',
      dob: '',
      guardianName: '',
      interestedClasses: '',
      insuranceDetails: '',
      clientId: null,
      communicationMode: [],
      remainder: null,
      repeatsOn: {
        sunday: false,
        monday: false,
        tuesday: false,
        wednesday: false,
        thursday: false,
        friday: false,
        saturday: false,
      },
      startDate: '',
      endDate: '',
      startTime: '',
      endTime: '',
      duration: '',
      validate: true
    }
  }

  public breakpoint = {
    list: 2,
    search: 2,
    tileOne: 1,
    tileTwo: 2,
    tileThree: 3,
    tileFour: 4,
    tileFive: 5,
    tileSix: 6,
    tileSeven: 7,
    tileEight: 8,
    tileNine: 9,
  };

  public breakpointDesktop = {
    list: 2,
    search: 2,
    tileOne: 1,
    tileTwo: 2,
    tileThree: 3,
    tileFour: 4,
    tileFive: 5,
    tileSix: 6,
    tileSeven: 7,
    tileEight: 8,
    tileNine: 9,
  };

  public breakpointMobile = {
    list: 2,
    search: 1,
    tileOne: 10,
    tileTwo: 10,
    tileThree: 10,
    tileFour: 10,
    tileFive: 10,
    tileSix: 10,
    tileSeven: 10,
    tileEight: 10,
    tileNine: 10
  };

  public repeatsOn() {
    return {
      sunday: [''],
      monday: [''],
      tuesday: [''],
      wednesday: [''],
      thursday: [''],
      friday: [''],
      saturday: ['']
    }
  }

  public therapistMandatoryFields() {
    return {
      firstName: [null],
      lastName: [null],
      gender: [null],
      email: [null],
      phone: [null],
      dob: [null],
      therapistType: [null],
      sessionsCovered: [null],
      repeats: [null],
      startDate: [null],
      endDate: [null],
      startTime: [null],
      endTime: [null],
    }
  }

  public therapistNonMandatoryFields() {
    return {
      avatarUrl: ["./assets/images/img_avatar.png"],
      certifications: [null],
      therapistId: [null],
      levelName: [null],
      levelId: [null],
      duration: [null],
      repeatsEvery: [null],
      remainder: [null],
      validate: [null]
    }
  }

  public clientMandatoryFields() {
    return {
      firstName: [null],
      lastName: [null],
      gender: [null],
      email: [null],
      phone: [null],
      dob: [null],
      startDate: [null],
      endDate: [null],
      startTime: [null],
      endTime: [null]
    }
  }

  public clientNonMandatoryFields() {
    return {
      sessionsCovered: [null],
      guardianRelation: [null],
      addressOne: [null],
      addressTwo: [null],
      profile: [],
      clientId: [null],
      addresses: [],
      avatarUrl: ["./assets/images/img_avatar.png"],
      interestedClasses: [null],
      guardianName: [null],
      insuranceDetails: [null],
      duration: [null],
      remainder: [null],
      validate: [null]
    }
  }

  public addressValues() {
    return {
      siteId: null,
      type: '',
      addressOne: '',
      addressTwo: '',
      city: '',
      state: '',
      country: '',
      zip: ''
    }
  };

  public weeksList() {
    return [{
      value: 1,
      display: 1
    }, {
      value: 2,
      display: 2
    }, {
      value: 3,
      display: 3
    }, {
      value: 4,
      display: 4
    }];
  }


  public addMandatory(object: any, validator: any) {
    Object.keys(object).forEach(function (key) {
      object[key].push(validator);
    })
    return object;
  }

}
