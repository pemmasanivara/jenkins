/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { FormGroup, FormControl, Validators, FormBuilder, FormGroupDirective, NgForm, FormArray, ValidatorFn } from '@angular/forms';
import { Observable } from 'rxjs';
import { LLKHttpService, msgResource, UtilService, InitValueService, FormatValueService } from '../../shared';
import { FileInput } from 'ngx-material-file-input';
import { tap } from 'rxjs/internal/operators/tap';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AddressFormComponent } from '../../shared/';



export interface User {
  firstName: string,
  lastName: string,
  email: string,
  phone: string,
  avatarUrl: string,
  clientId: number
}

@Component({
  selector: 'clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss'],
  host: {
    '(window:resize)': 'onResize($event)'
  }
})

export class ClientsComponent implements OnInit {
  public clientData: Observable<Array<any>>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('clientForm') clientForm: NgForm;

  public innerWidth: any;

  public page: number = 1;
  public perPage: number = 10;
  public totalPages: number = 0;
  public minDate = this.formatValueService.deltaDate(new Date(), 0, 0, -200);
  public maxDate = new Date();
  public startMinDate = new Date();
  public startMaxDate = this.formatValueService.deltaDate(new Date(), 0, 0, 200);
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
  public searchClientList;
  public communicationmodesList;
  public therapyTypesList: User[];
  public breakpoint = this.initValueService.breakpoint;
  public formGroup: FormGroup;
  public uploadFormGroup: FormGroup;
  public post: any = '';
  public sessionsCovered = {};
  public levelName = {};
  public currentClient;
  public current_session;
  public isEditMode = false;
  public searchText;
  public remaindersList;
  public repeatsList;
  public gendersList;
  public activePageDataChunk = [];
  public submitted = false;
  public currentFormData;
  public addressOne = this.initValueService.addressValues();
  public addressTwo = this.initValueService.addressValues();

  constructor(private router: Router, private snackBar: MatSnackBar, public dialog: MatDialog, private formatValueService: FormatValueService, private cdr: ChangeDetectorRef, private utilService: UtilService, private initValueService: InitValueService, private llkHttpService: LLKHttpService, private changeDetectorRef: ChangeDetectorRef, private formBuilder: FormBuilder) {
    this.initService();
  }

  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>(this.searchClientList);
  //method to get  data
  public initService() {
    this.levels();
    this.communicationmodes();
    this.therapyTypes();
    this.getRemainders();
    this.getRepeats();
    this.getGenders();
    this.searchClient(null, 0, 10);
  }

  public cancel(isEditMode) {
    if (isEditMode) {
      this.editUser(this.currentClient);
    } else {
      this.addNewUser();
    }
  }

  public addClient(action) {
    this.clientForm.ngSubmit.emit();
    if (this.clientForm.invalid) {
      return;
    }
    let currentObject = this.formGroup.value;
    currentObject.communicationMode = this.formGroup.value.communicationMode.map((v, i) => v ? this.communicationmodesList[i] : null).filter(v => v !== null);
    let data = this.formatValueService.formatAddClient(currentObject);
    data.addresses.push(this.addressOne, this.addressTwo);
    const file_form: FileInput = this.uploadFormGroup.get('profileImg').value;
    const file = file_form ? file_form.files[0] : null;
    var tempBlob = new Blob([JSON.stringify(data)], { type: "application/json" }), fr = new FileReader();
    const formData = new FormData();
    if (file) {
      formData.append('profileImg', file, file.name);
    }
    formData.append('client', tempBlob);
    this.saveClient(formData, action);
  }

  public updateClient(action) {
    this.clientForm.ngSubmit.emit();
    if (this.clientForm.invalid) {
      return;
    }
    let currentObject = this.formGroup.value;
    currentObject.communicationMode = this.formGroup.value.communicationMode.map((v, i) => v ? this.communicationmodesList[i] : null).filter(v => v !== null);
    let data = this.formatValueService.formatUpdateClient(currentObject);
    data.addresses.push(this.addressOne, this.addressTwo);
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.updateClient;
    this.llkHttpService.putService(data, url).subscribe(
      (data: any) => {
        this.handleAfterUpdate(action);
      });
  }

  public navigateWithState(isEditMode) {
    this.clientForm.ngSubmit.emit();
    this.currentFormData = this.formGroup.value;
    if (this.clientForm.invalid) {
      this.handleAfterUpdate('calendar');
    } else {
      if (isEditMode) {
        this.updateClient('calendar');
      } else {
        this.addClient('calendar');
      }
    }
  }

  public handleAfterUpdate(action: any) {
    if (action === 'form') {
      this.openSnackBar("Successfully updated", "");
      this.newUser = false;
      this.searchClient(this.searchText, 0, 10);
    } else {
      setTimeout(() => {
        this.router.navigateByUrl('/calendar', { state: { page: 'client', data: this.currentFormData } });
      }, 0);
    }
  }

  public handleAfterSave(action: any) {
    if (action === 'form') {
      this.openSnackBar("Successfully Saved", "");
      this.createForm();
      this.searchText = null;
      this.newUser = false;
      this.searchClient(null, 0, 10);
    } else {
      this.router.navigateByUrl('/calendar', { state: { page: 'client', data: this.currentFormData } });
    }
  }
  public handleSearch(text: any) {
    this.searchClient(text, 0, 10);
    this.newUser = false;
  }

  public searchClient(text: any, start: any, limit: any) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.searchClient;
    url = this.formatValueService.constructSearchUrl(url, text, start, limit);
    this.callGetService(url, "searchClientList");
  }

  public saveClient(post, action) {
    this.post = post;
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.addClient;
    this.llkHttpService.postService(post, url).subscribe(
      (data: any) => {
        this.handleAfterSave(action);
      });
  }

  public deleteClient(detail) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.deleteClient + detail.clientId;
    this.llkHttpService.deleteService(url).subscribe(
      (data: any) => {
        this.searchClient(this.searchText, ((this.page * this.perPage) - this.perPage), this.perPage);
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

  public getClient(detail) {
    let url = this.utilService.getApiUrl(this.configAppUrls) + this.configPatchUrls.detailClient + detail.clientId;
    this.callGetService(url, "getClient");
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
      case "searchClientList":
        if (data) {
          this.searchClientList = data.clients;
          /*  this.paginator.page.pipe(
              tap(() => this.loaduserPage())
            ).subscribe();*/
          //this.totalPages = 10;
        }
        break;
      case "getClient":
        if (data) {
          let parentObj = this.initValueService.clientFormGroupInit();
          this.currentClient = { ...parentObj, ...data };
          this.editUser(this.currentClient);
        }
        break;
      default:
        break;
    }
  }

  loaduserPage() {

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
    this.addressOne = this.initValueService.addressValues();
    this.addressTwo = this.initValueService.addressValues();
    this.createForm();
  }

  public createForm() {
    let clientMandatoryGroup = this.initValueService.addMandatory(this.initValueService.clientMandatoryFields(), Validators.required);
    let clientNonMandatoryGroup = this.initValueService.clientNonMandatoryFields();
    let repeatsOn = { repeatsOn: this.formBuilder.group(this.initValueService.repeatsOn()) }
    let communicationMode = { communicationMode: new FormArray([], this.minSelectedCheckboxes(1)) };
    let clientGroup = { ...clientMandatoryGroup, ...clientNonMandatoryGroup, ...repeatsOn, ...communicationMode };
    this.formGroup = this.formBuilder.group(clientGroup);
    this.addCheckboxes();
    this.uploadFormGroup = this.formBuilder.group({
      profileImg: null
    });
  };

  public openDialog(address) {
    const dialogRef = this.dialog.open(AddressFormComponent, {
      width: '500px',
      data: this.setDialogData(address)
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.siteId === 1) {
        this.addressOne = result;
        let addressOneList = [...this.utilService.json2array(this.addressOne)];
        addressOneList.shift();
        this.formGroup.value.addressOne = addressOneList;
      } else {
        this.addressTwo = result;
        let addressTwoList = [...this.utilService.json2array(this.addressTwo)];
        addressTwoList.shift();
        this.formGroup.value.addressTwo = addressTwoList;
      }
      this.formGroup.patchValue(this.formGroup.value);
    });
  }

  public setDialogData(siteId) {
    if (siteId === 1) {
      let dialogAddress = this.addressOne;
      dialogAddress["siteId"] = siteId;
      return dialogAddress
    } else {
      let dialogAddress = this.addressTwo;
      dialogAddress["siteId"] = siteId;
      return dialogAddress
    }
  }

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
    this.addressOne = { ...this.addressOne, ...detail.addresses[0] };
    this.addressTwo = { ...this.addressTwo, ...detail.addresses[1] };
    let addressOneList = [...this.utilService.json2array(this.addressOne)];
    addressOneList.shift();
    let addressTwoList = [...this.utilService.json2array(this.addressTwo)];
    addressTwoList.shift();
    detail.addressOne = addressOneList;
    detail.addressTwo = addressTwoList;
    this.formGroup.patchValue(detail);
  }

  public compareClientType(c1: { levelId: string }, c2: { levelId: string }) {
    return c1 && c2 && c1.levelId === c2.levelId;
  }

  public compare(c1: any, c2: any) {
    return c1 && c2 && c1 === c2;
  }

  public compareTherapy(c1: { therapyId: string }, c2: { therapyId: string }) {
    return c1 && c2 && c1.therapyId === c2.therapyId;
  }

  public compareCommunicationmodes(c1: { value: string }, c2: { value: string }) {
    return c1 && c2 && c1.value === c2.value;
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
      this.endMinDate = this.startMinDate;
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
