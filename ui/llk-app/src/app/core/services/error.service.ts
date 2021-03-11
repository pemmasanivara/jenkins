/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  getClientErrorMessage(error): string {
    let errorMsg = error.toString();
    if (error && error.error && error.error.message) {
      errorMsg = error.error.message;
    } else if (error && error.message) {
      errorMsg = error.message;
    }
    return errorMsg;
  }

  getServerErrorMessage(error): string {
    let errorMsg = 'No Internet Connection';
    if (navigator.onLine && error && error.error && error.error.message) {
      errorMsg = error.error.message;
    } else if (navigator.onLine && error && error.message) {
      errorMsg = error.message;
    }
    return errorMsg;
  }
}