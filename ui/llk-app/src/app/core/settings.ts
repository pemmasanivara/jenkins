/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
export interface AppSettings {
  navPos?: 'side' | 'top';
  theme?: 'light' | 'dark';
  dir?: 'ltr' | 'rtl';
  showHeader?: boolean;
  headerPos?: 'fixed' | 'static' | 'above';
  showUserPanel?: boolean;
  sidenavOpened?: boolean;
  sidenavCollapsed?: boolean;
}

export const defaults: AppSettings = {
  navPos: 'side',
  theme: 'light',
  dir: 'ltr',
  showHeader: true,
  headerPos: 'fixed',
  showUserPanel: true,
  sidenavOpened: true,
  sidenavCollapsed: false,
};
