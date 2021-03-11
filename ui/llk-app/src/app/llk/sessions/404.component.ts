/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-error-404',
  template: `
    <error-code
      code="404"
      [title]="'Page not found!'"
      [message]="'This is not the web page you are looking for.'"
    ></error-code>
  `,
})
export class Error404Component implements OnInit {
  constructor() {}

  ngOnInit() {}
}