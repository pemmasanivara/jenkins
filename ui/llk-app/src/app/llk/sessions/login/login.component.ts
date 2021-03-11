/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService:AuthenticationService) { }

  ngOnInit() {  }

  checkoutAccount() {
    this.authService.checkoutAccount();
  }

  login() {
    this.authService.login();
  }

  logout() {
    this.authService.logout();
  }

}
