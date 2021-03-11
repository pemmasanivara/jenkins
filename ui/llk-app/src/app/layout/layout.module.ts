/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';

import { pageLayoutComponent } from './page-layout/page-layout.component';
import { SidebarComponent } from './page-layout/sidebar/sidebar.component';
import { SidemenuComponent } from './page-layout/sidemenu/sidemenu.component';
import { AccordionAnchorDirective } from './page-layout/sidemenu/accordionanchor.directive';
import { AccordionDirective } from './page-layout/sidemenu/accordion.directive';
import { AccordionLinkDirective } from './page-layout/sidemenu/accordionlink.directive';
import { HeaderComponent } from './page-layout/header/header.component';
import { BrandingComponent } from './page-layout/header/branding.component';
import { NotificationComponent } from './page-layout/header/notification.component';
import { UserComponent } from './page-layout/header/user.component';
import { AuthLayoutComponent } from './auth-layout/auth-layout.component';

@NgModule({
  declarations: [
    pageLayoutComponent,
    SidebarComponent,
    SidemenuComponent,
    AccordionAnchorDirective,
    AccordionDirective,
    AccordionLinkDirective,
    HeaderComponent,
    BrandingComponent,
    NotificationComponent,
    UserComponent,
    AuthLayoutComponent,
  ],
  imports: [SharedModule],
})
export class ThemeModule {}
