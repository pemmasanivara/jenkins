/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule, APP_INITIALIZER, ErrorHandler } from '@angular/core';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { ThemeModule } from './layout/layout.module';
import { RoutesModule } from './llk/llk.module';
import { msgResource } from './shared';
import { DefaultInterceptor } from './core';
import { StartupService } from './core';
import { GlobalErrorHandler } from './core/interceptors/global-error-handler';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { MsalModule, MsalService, MSAL_CONFIG_ANGULAR, MsalAngularConfiguration, MsalInterceptor, MSAL_CONFIG } from '@azure/msal-angular';
import { Configuration } from 'msal';

export function StartupServiceFactory(startupService: StartupService) {
  return () => startupService.load();
}
// msal config
const isIE = window.navigator.userAgent.indexOf("MSIE ") > -1 || window.navigator.userAgent.indexOf("Trident/") > -1;
export function getMsalConfig(): Configuration {
  var baseUrl = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ":" + window.location.port : "") + "/";
  var msal_clientId = (baseUrl.indexOf(msgResource.llkPortal.appUrls.web.prod) > -1) ? msgResource.llkPortal.msalConfig.clientId_prod : msgResource.llkPortal.msalConfig.clientId_dev;
  let redirectUri = baseUrl;
  return {
    auth: {
      clientId: msal_clientId,
      authority: msgResource.llkPortal.msalConfig.msalBaseUrl + msgResource.llkPortal.msalConfig.tenantId + '/',
      validateAuthority: true,
      redirectUri: redirectUri,
      postLogoutRedirectUri: redirectUri,
      navigateToLoginRequestUrl: true,
    },
    cache: {
      cacheLocation: "localStorage",
      storeAuthStateInCookie: isIE,
    },
  }
}

export function MSALAngularConfigFactory(): MsalAngularConfiguration {
  return {
    popUp: !isIE
  };
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CoreModule,
    SharedModule,
    ThemeModule,
    RoutesModule,
    MsalModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [
    MsalService,
    { provide: MSAL_CONFIG, useValue: getMsalConfig() },
    { provide: MSAL_CONFIG_ANGULAR, useFactory: MSALAngularConfigFactory },
    { provide: HTTP_INTERCEPTORS, useClass: DefaultInterceptor, multi: true },
    { provide: ErrorHandler, useClass: GlobalErrorHandler },
    StartupService,
    { provide: APP_INITIALIZER, useFactory: StartupServiceFactory, deps: [StartupService], multi: true }
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }