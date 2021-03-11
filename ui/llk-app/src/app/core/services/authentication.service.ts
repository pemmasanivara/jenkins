/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Injectable } from '@angular/core';
import { MsalService } from '@azure/msal-angular';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
  static idToken(arg0: string, idToken: any) {
    throw new Error("Method not implemented.");
  }

    public loggedIn = false;
    public idToken: string = null;

    get className() {
      return this.idToken;
    }
  
    set className(name) {
      this.idToken = name;
    }
    constructor(private authService: MsalService, private router: Router) { }

    public getMSALHeaderToken(): string {
        let msalToken = localStorage.getItem('msal.idtoken');
        msalToken = msalToken ? msalToken : '';
        return msalToken;
    }
    public checkoutAccount() {
        this.loggedIn = !!this.authService.getAccount();
    }

    public login() {
        const isIE = window.navigator.userAgent.indexOf('MSIE ') > -1 || window.navigator.userAgent.indexOf('Trident/') > -1;
        let _self = this;
        if (isIE) {
            this.authService.loginRedirect();
        } else {
            this.authService.loginPopup().then(function (loginResponse) {
                _self.idToken = loginResponse.idToken;
                _self.router.navigate(['/home']);
            }).catch(function (error) {
                _self.router.navigate(['/auth/login']);
            });
        }
    }

    public logout() {
        this.authService.logout();
    }

}
