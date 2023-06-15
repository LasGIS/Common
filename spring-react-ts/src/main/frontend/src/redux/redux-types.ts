/*
 *  @(#)redux-types.ts  last: 15.06.2023
 *
 * Title: LG prototype for spring-security + spring-data + react
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

import { RouterState } from "connected-react-router";

export type AppSettingsConfig = {
  printLocale?: boolean;
  name?: string;
  version?: string;
};

export type CommonStoreData = {
  loading?: boolean;
  settings?: AppSettingsConfig;
};

export type RootStoreData = {
  router: RouterState;
  common: CommonStoreData;
};
