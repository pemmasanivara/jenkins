/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { FormGroup, FormControl, Validators, FormBuilder, FormGroupDirective, NgForm, FormArray, ValidatorFn, Form } from '@angular/forms';
import { Observable } from 'rxjs';
import { LLKHttpService, msgResource, UtilService, InitValueService, FormatValueService } from '../../shared';
import { FileInput } from 'ngx-material-file-input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

export interface User {
  firstName: string,
  lastName: string,
  email: string,
  phone: string,
  avatarUrl: string,
  therapistId: number
}

@Component({
  selector: 'therapists',
  templateUrl: './therapists.component.html',
  styleUrls: ['./therapists.component.scss'],
  host: {
    '(window:resize)': 'onResize($event)'
  }
})

export class TherapistsComponent implements OnInit {
  public clientData: Observable<Array<any>>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('therapistForm') therapistForm: NgForm;

  public page: number = 1;
  public perPage: number = 10;
  public totalPages: number = 0;
  public minDate = this.formatValueService.deltaDate(new Date(), 0, 0, -200);
  public maxDate = new Date();
  public startMinDate = new Date();
  public startMaxDate = this.formatValueService.deltaDate(new Date(), 0, 0, 200);;
  public endMinDate = new Date();
  public startMinTime = "00:00";
  public startMaxTime = "23:59";
  public endMinTime = "00:00";
  public endMaxTime = "23:59";


  // MatPaginator Inputs
  public user = {};
  public newUser = false;

  // MatPaginator Output
  public pageEvent: PageEvent;

  public msgConstants: any = msgResource;
  public configAppUrls: any = msgResource.llkPortal.appUrls;
  public configPatchUrls: any = msgResource.llkPortal.appUrls.patchUrls;
  public levelsList;
  public searchTherapistList;
  public communicationmodesList;
  public therapyTypesList: User[];
  public breakpoint = this.initValueService.breakpoint;
  public formGroup: FormGroup;
  public uploadFormGroup: FormGroup;
  public post: any = '';
  public sessionsCovered = {};
  public levelName = {};
  public currentTherapist;
  public current_session;
  public isEditMode = false;
  public searchText;
  public remaindersList;
  public repeatsList;
  public gendersList;
  public activePageDataChunk = [];
  public currentFormData;

  constructor(private router: Router, private snackBar: MatSnackBar, private cdr: ChangeDetectorRef, private formatValueService: FormatValueService, private utilService: UtilService, private initValueService: InitValueService, private llkHttpService: LLKHttpService, private changeDetectorRef: ChangeDetectorRef, private formBuilder: FormBuilder) {
    this.initService();
  }

  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>(this.searchTherapistList);
  //method to get  data
  public initService() {
    this.levels();
    this.communicationmodes();
    this.therapyTypes();
    this.getRemainders();
    this.getRepeats();
    this.getGenders();
    this.searchTherapist(null, 0, 10);
  }

  // cancel Edit
  public cancel(isEditMode) {
    if (isEditMode) {
      this.editUser(this.currentTherapist);
    } else {
      this.addNewUser();
    }
  }

  // Add Therapist
  public addTherapist(action) {
    this.therapistForm.ngSubmit.emit();
    if (this.therapistForm.invalid) {
      return;
    }
    let currentObject = this.formGroup.value;
    currentObject.communicationMode = this.formGroup.value.communicationMode.map((v, i) => v ? this.communicationmodesList[i] : null).filter(v => v !== null);
    let data = this.formatValueService.formatAddTherapist(currentObject);
    this.currentFormData = data;
    const file_form: FileInput = this.uploadFormGroup.get('profileImg').value;
    const file = file_form ? file_form.files[0] : null;
    var tempBlob = new Blob([JSON.stringify(data)], { type: "application/json" }), fr = new FileReader();
    const formData = new FormData();
    if (file) {
      formData.append('profileImg', file, file.name);
    }
    formData.append('therapist', tempBlob);
    this.saveTherapist(formData, action);
  }

  public weeksList = this.initValueService.weeksList();

  public saveTherapist(post, action) {
    this.post = post;
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.addTherapist;
    this.llkHttpService.postService(post, url).subscribe(
      (data: any) => {
        this.handleAfterSave(action);
      });
  }

  public updateTherapist(action) {
    this.therapistForm.ngSubmit.emit();
    if (this.therapistForm.invalid) {
      return;
    }
    let currentObject = this.formGroup.value;
    currentObject.communicationMode = this.formGroup.value.communicationMode.map((v, i) => v ? this.communicationmodesList[i] : null).filter(v => v !== null);
    let data = this.formatValueService.formatUpdateTherapist(currentObject);
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.updateTherapist;
    this.llkHttpService.putService(data, url).subscribe(
      (data: any) => {
        this.handleAfterUpdate(action);
      });
  }

  public navigateWithState(isEditMode) {
    this.therapistForm.ngSubmit.emit();
    this.currentFormData = this.formGroup.value;
    if (this.therapistForm.invalid) {
      this.handleAfterUpdate('calendar');
    } else {
      if (isEditMode) {
        this.updateTherapist('calendar');
      } else {
        this.addTherapist('calendar');
      }
    }
  }

  public handleAfterUpdate(action: any) {
    if (action === 'form') {
      this.openSnackBar("Successfully updated", "");
      this.newUser = false;
      this.searchTherapist(this.searchText, 0, 10);
    } else {
      setTimeout(() => {
        this.router.navigateByUrl('/calendar', { state: { page: 'therapist', data: this.currentFormData } });
      }, 0);
    }
  }

  public handleAfterSave(action: any) {
    if (action === 'form') {
      this.openSnackBar("Successfully Saved", "");
      this.createForm();
      this.searchText = null;
      this.newUser = false;
      this.searchTherapist(null, 0, 10);
    } else {
      this.router.navigateByUrl('/calendar', { state: { page: 'therapist', data: this.currentFormData } });
    }
  }

  public handleSearch(text: any) {
    this.searchTherapist(text, 0, 10);
    this.newUser = false;
  }

  public searchTherapist(text: any, start: any, limit: any) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.searchTherapist;
    url = this.formatValueService.constructSearchUrl(url, text, start, limit);
    this.callGetService(url, "searchTherapistList");
  }

  public deleteTherapist(detail) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.deleteTherapist + detail.therapistId;
    this.llkHttpService.deleteService(url).subscribe(
      (data: any) => {
        this.openSnackBar("Successfully Deleted", "");
        this.searchTherapist(this.searchText, ((this.page * this.perPage) - this.perPage), this.perPage);
      });
  }

  public getRemainders() {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.remainders;
    this.callGetService(url, "remaindersList");
  }

  public getRepeats() {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.repeats;
    this.callGetService(url, "repeatsList");
  }

  public getGenders() {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.genders;
    this.callGetService(url, "gendersList");
  }

  public getTherapist(detail) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.detailTherapist + detail.therapistId;
    this.callGetService(url, "getTherapist");
  }

  public levels() {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.levels;
    this.callGetService(url, "levelsList");
  }

  public communicationmodes() {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.communicationmodes;
    this.callGetService(url, 'communicationmodesList');
  }

  public therapyTypes() {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.therapyTypes;
    this.callGetService(url, 'therapyTypesList');
  }

  public callGetService(url: any, list: any) {
    this.llkHttpService.getService(url).subscribe(
      (data: any) => {
        this.setData(data, list);
      });
  }

  public setData(data: any, list: any) {
    if (!data) {
      this.openSnackBar("serviceFailed", list);
    }
    switch (list) {
      case "levelsList":
        this.levelsList = data;
        break;
      case "communicationmodesList":
        this.communicationmodesList = data;
        break;
      case "therapyTypesList":
        this.therapyTypesList = data;
        break;
      case "remaindersList":
        this.remaindersList = data;
        break;
      case "repeatsList":
        this.repeatsList = data;
        break;
      case "gendersList":
        this.gendersList = data;
        break;
      case "searchTherapistList":
        if (data) {
          this.searchTherapistList = data.therapists;
          //this.totalPages = 10;// data.totalCount/10;
        }
        break;
      case "getTherapist":
        if (data) {
          let parentObj = this.initValueService.therapistFormGroupInit();
          this.currentTherapist = { ...parentObj, ...data }
          this.currentTherapist.therapistType = this.currentTherapist.levelId;
          this.editUser(this.currentTherapist);
        }
        break;
      default:
        break;
    }
  }

  public onResize(event) {
    this.breakpoint = (event.target.innerWidth <= 811) ? this.initValueService.breakpointMobile : this.initValueService.breakpointDesktop;
    this.breakpoint.list = (event.target.innerWidth <= 567) ? 1 : 2;
  }

  ngOnInit() {
    this.breakpoint = (window.innerWidth <= 811) ? this.initValueService.breakpointMobile : this.initValueService.breakpointDesktop;
    this.breakpoint.list = (window.innerWidth <= 567) ? 1 : 2;
  }

  public onPageChanged(e) {
    let firstCut = e.pageIndex * e.pageSize;
    let secondCut = firstCut + e.pageSize;
  }

  ngOnDestroy() {

  }

  public closeNewUser() {
    this.newUser = false;
    this.isEditMode = false;
  }

  public addNewUser() {
    this.newUser = true;
    this.isEditMode = false;
    this.createForm();
  }

  public createForm() {
    let repeatsOn = { repeatsOn: this.formBuilder.group(this.initValueService.repeatsOn()) };
    let therapistMandatoryGroup = this.initValueService.addMandatory(this.initValueService.therapistMandatoryFields(), Validators.required);
    let therapistNonMandatoryGroup = this.initValueService.therapistNonMandatoryFields();
    let communicationMode = { communicationMode: new FormArray([], this.minSelectedCheckboxes(1)) };
    let therapistGroup = { ...therapistNonMandatoryGroup, ...therapistMandatoryGroup, ...repeatsOn, ...communicationMode };
    this.formGroup = this.formBuilder.group(therapistGroup);
    this.addCheckboxes();
    this.uploadFormGroup = this.formBuilder.group({
      profileImg: null
    });
  };

  public getcommunicationMode() {
    return this.formGroup.get('communicationMode') as FormArray;
  }

  public addCheckboxes() {
    if (this.communicationmodesList) {
      this.communicationmodesList.forEach((o, i) => {
        const control = new FormControl();
        (this.formGroup.controls.communicationMode as FormArray).push(control);
      });
    }
  }
  getControls() {
    return (this.formGroup.get('controlName') as FormArray).controls;
  }
  public minSelectedCheckboxes(min = 1) {
    const validator: ValidatorFn = (formArray: FormArray) => {
      const totalSelected = formArray.controls
        .map(control => control.value ? 1 : 0)
        .reduce((prev, next) => next ? prev + next : prev, 0);
      return totalSelected >= min ? null : { required: true };
    };
    return validator;
  }

  public editUser(detail: any) {
    this.newUser = true;
    this.isEditMode = true;
    this.createForm();
    detail.dob = new Date(detail.dob);
    this.formGroup.patchValue(detail);
  }

  public compareTherapistType(c1: { levelId: string }, c2: { levelId: string }) {
    return c1 && c2 && c1.levelId === c2.levelId;
  }

  public compare(c1: any, c2: any) {
    return c1 && c2 && c1 === c2;
  }

  public compareTherapy(c1: { therapyId: string }, c2: { therapyId: string }) {
    return c1 && c2 && c1.therapyId === c2.therapyId;
  }

  public compareCommunicationmodes(c1: { display: string }, c2: { display: string }) {
    return c1 && c2 && c1.display === c2.display;
  }

  public openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  public onStartDateChange(event) {
    if (event.value) {
      this.endMinDate = event.value;
    } else {
      this.endMinDate = this.endMinDate;
    }
  }

  public onEndDateChange(event) {
    if (event.value) {
      this.startMaxDate = event.value;
    } else {
      this.startMaxDate = this.maxDate;
    }
  }

  public onStartTimeChange(value) {
    if (value) {
      this.endMinTime = value;
    }
  }

  public onEndTimeChange(value) {
    if (value) {
      this.startMaxTime = value;
    }
  }
}
