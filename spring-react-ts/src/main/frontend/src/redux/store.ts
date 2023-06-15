/*
 *  @(#)store.ts  last: 15.06.2023
 *
 * Title: LG prototype for spring-security + spring-data + react
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

import { configureStore } from "@reduxjs/toolkit";
import Common from "./common";

const store = configureStore({
  reducer: {
    common: Common,
  },
});

export default store;
