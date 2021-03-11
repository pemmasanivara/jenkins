/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-error-500',
  template: `
    <error-code
      code="500"
      [title]="'Server went wrong!'"
      [message]="'Just kidding, looks like we have an internal issue, please try refreshing.'"
    >
    </error-code>
  `,
})
export class Error500Component implements OnInit {
  constructor() {}

  ngOnInit() {}
}
