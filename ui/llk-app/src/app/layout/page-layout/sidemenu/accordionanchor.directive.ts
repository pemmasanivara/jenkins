/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { Directive, HostListener, Inject } from '@angular/core';

import { AccordionLinkDirective } from './accordionlink.directive';

@Directive({
  selector: '[navAccordionToggle]',
})
export class AccordionAnchorDirective {
  protected navlink: AccordionLinkDirective;

  constructor(@Inject(AccordionLinkDirective) navlink: AccordionLinkDirective) {
    this.navlink = navlink;
  }

  @HostListener('click', ['$event'])
  onClick() {
    this.navlink.toggle();
  }
}
