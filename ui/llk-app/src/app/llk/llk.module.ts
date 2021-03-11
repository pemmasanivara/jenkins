/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { LLKRoutingModule } from './llk-routing.module';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomeComponent } from './home/home.component';
import { CalendarComponent } from './calendar/calendar.component';
import { ClientsComponent } from './clients/clients.component';
import { InboxComponent } from './inbox/inbox.component';
import { InvoicesComponent } from './invoices/invoices.component';
import { TherapistsComponent } from './therapists/therapists.component';
import { LoginComponent } from './sessions/login/login.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

const COMPONENTS = [
  DashboardComponent,
  HomeComponent,
  CalendarComponent,
  ClientsComponent,
  InboxComponent,
  InvoicesComponent,
  TherapistsComponent,
  LoginComponent,
];

const COMPONENTS_DYNAMIC = [];

const MODULE_IMPORTS = [SharedModule,
  LLKRoutingModule,
  FullCalendarModule,
  MaterialFileInputModule,
  NgxMaterialTimepickerModule];

@NgModule({
  imports: [...MODULE_IMPORTS],
  declarations: [...COMPONENTS, ...COMPONENTS_DYNAMIC],
  schemas: [NO_ERRORS_SCHEMA],
  entryComponents: COMPONENTS_DYNAMIC,
})
export class RoutesModule { }
