/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UtilService {

  // Method to get env url
  public getApiUrl(appUrls: any) {
    let path = window.location.hostname;
    let apiBaseUrl = '';
    let webUrls = appUrls.web;
    let apiUrls = appUrls.api;
    apiBaseUrl = this.mapValue(path, webUrls, apiUrls);
    return apiBaseUrl;
  }

  // Method to get env url
  public getVersion(appUrls: any) {
    let path = window.location.hostname;
    let version = '';
    let webUrls = appUrls.web;
    let versions = appUrls.version;
    version = this.mapValue(path, webUrls, versions);
    return version;
  }

  // Method to Map corresponding values
  public mapValue(appPath: any, keyObject: any, mapObject: any) {
    let apiBaseUrl = '';
    Object.keys(keyObject).forEach(key => {
      if (keyObject[key] === appPath) {
        apiBaseUrl = mapObject[key];
      }
    });
    return apiBaseUrl;
  }

  // Method to convert JSON to Array
  public json2array(json) {
    var result = [];
    var keys = Object.keys(json);
    keys.forEach(function (key) {
      result.push(json[key]);
    });
    return result;
  }

  public mergeArray(a, b, prop) {
    var reduced = a.filter(aitem => !b.find(bitem => aitem[prop] && aitem[prop].toString() === bitem[prop] && bitem[prop].toString()))
    return reduced.concat(b);
  }
}
