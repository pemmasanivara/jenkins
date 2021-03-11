/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { NgModule,NO_ERRORS_SCHEMA, CUSTOM_ELEMENTS_SCHEMA  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { MaterialModule } from '../material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { PageHeaderComponent } from './components/page-header/page-header.component';
import { ErrorCodeComponent } from './components/error-code/error-code.component';
import { PaginatorStyleDirective } from './directive/paginator-style.directive';
import { NameValidationDirective } from './directive/name-validation.directive';
import { EmailValidationDirective } from './directive/email-validation.directive';
import { PhoneValidationDirective } from './directive/phone-validation.directive';
import { StringValidationDirective } from './directive/string-validation.directive';
import { AddressFormComponent } from './components/address/address-form.component';
import { EventBookingComponent } from './components/event-booking/event-booking.component';


const THIRD_MODULES = [
  MaterialModule,
  FlexLayoutModule,
  FormlyMaterialModule,
];
const COMPONENTS = [
  PageHeaderComponent,
  ErrorCodeComponent,
  AddressFormComponent,
  EventBookingComponent
];
const COMPONENTS_DYNAMIC = [];
const DIRECTIVES = [
  PaginatorStyleDirective,
  NameValidationDirective,
  EmailValidationDirective,
  PhoneValidationDirective,
  StringValidationDirective
];
const PIPES = [];

@NgModule({
  declarations: [...COMPONENTS, ...COMPONENTS_DYNAMIC, ...DIRECTIVES, ...PIPES],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    FormlyModule.forRoot(),
    ...THIRD_MODULES,
  ],
  exports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReactiveFormsModule,
    FormlyModule,
    ...THIRD_MODULES,
    ...COMPONENTS,
    ...DIRECTIVES,
    ...PIPES,
  ],
  schemas: [NO_ERRORS_SCHEMA,CUSTOM_ELEMENTS_SCHEMA],
  entryComponents: COMPONENTS_DYNAMIC,
})
export class SharedModule { }
