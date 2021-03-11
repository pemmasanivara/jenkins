/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Component } from '@angular/core';
import { AuthenticationService } from '../../../core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html'
})
export class UserComponent { 
  constructor(private authService:AuthenticationService) { }
  public logout(){
    this.authService.logout();
  }
}
