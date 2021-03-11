/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class LLKHttpService {

	constructor(private http: HttpClient) { }

	// Method to call getService
	public getService(url: any): Observable<any> {
		return this.http.get(url);
	}

	// Method to call deleteService
	public deleteService(url: any): Observable<any> {
		return this.http.delete(url);
	}

	// Method to call putService
	public putService(data: any, url: any): Observable<any> {
		return this.http.put(url, data);
	}

	// Method to call postService
	public postService(data: any, url: any) {
		return this.http.post(url, data, {
			responseType: 'blob',
		});
	}

}

