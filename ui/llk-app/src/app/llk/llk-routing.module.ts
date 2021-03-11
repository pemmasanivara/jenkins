/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { environment } from '../../environments/environment';
import { pageLayoutComponent } from '../layout/page-layout/page-layout.component';
import { AuthLayoutComponent } from '../layout/auth-layout/auth-layout.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './sessions/login/login.component';
import { HomeComponent } from './home/home.component';
import { CalendarComponent } from './calendar/calendar.component';
import { ClientsComponent } from './clients/clients.component';
import { InboxComponent } from './inbox/inbox.component';
import { InvoicesComponent } from './invoices/invoices.component';
import { TherapistsComponent } from './therapists/therapists.component';
import { MsalGuard } from '@azure/msal-angular';

const routes: Routes = [
  {
    path: '',
    component: pageLayoutComponent,
    children: [
      { path: '', redirectTo: '/auth/login', pathMatch: 'full' },
      {
        path: 'home',
        component: HomeComponent,
        data: { title: 'Home', titleI18n: 'home', canActivate: [MsalGuard] },
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
        data: { title: 'Dashboard', titleI18n: 'dashboard', canActivate: [MsalGuard] },
      },
      {
        path: 'inbox',
        component: InboxComponent,
        data: { title: 'Inbox', titleI18n: 'home', canActivate: [MsalGuard] },
      },
      {
        path: 'invoices',
        component: InvoicesComponent,
        data: { title: 'Invoices', titleI18n: 'invoices', canActivate: [MsalGuard] },
      },
      {
        path: 'therapists',
        component: TherapistsComponent,
        data: { title: 'Therapists', titleI18n: 'therapists', canActivate: [MsalGuard] },
      },
      {
        path: 'clients',
        component: ClientsComponent,
        data: { title: 'Clients', titleI18n: 'clients', canActivate: [MsalGuard] },
      },
      {
        path: 'calendar',
        component: CalendarComponent,
        data: { title: 'Calendar', titleI18n: 'calendar', canActivate: [MsalGuard] },
      },
      {
        path: 'sessions',
        loadChildren: () => import('./sessions/sessions.module').then(m => m.SessionsModule),
        data: { title: 'Sessions', titleI18n: 'Sessions' },
      },
    ],
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent,
        data: { title: 'Login', titleI18n: 'Login' },
      }
    ],
  },
  { path: '**', redirectTo: 'home', canActivate: [MsalGuard] },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      useHash: environment.useHash,
    }),
  ],
  exports: [RouterModule],
})
export class LLKRoutingModule { }
