/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpErrorResponse,
} from '@angular/common/http';
import {AuthenticationService} from '../services/authentication.service'

import { Observable, of, throwError } from 'rxjs';
import { mergeMap, catchError, retry, map } from 'rxjs/operators';

/** Pass untouched request through to the next request handler. */
@Injectable()
export class DefaultInterceptor implements HttpInterceptor {
  constructor(private authService: AuthenticationService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.handleRequest(request, next);
  }

  // handle Request
  public handleRequest(request: HttpRequest<any>, next: HttpHandler) {
    request = this.getAuthHeaderRequest(request);
    return next.handle(request).pipe(
      retry(1),
      map((event: any) => {
        return event;
      }),
      catchError((error: any) => {
        return throwError(error);
      }));
  }

  // Method to add header token
  public getAuthHeaderRequest(request: HttpRequest<any>) {
    const timeStamp = new Date().getTime().toString();
    let headers = request.headers
      .set('time-stamp', timeStamp)
      .set('authorization', 'Bearer '+this.authService.getMSALHeaderToken());
    return request.clone({ headers });
  }

}

