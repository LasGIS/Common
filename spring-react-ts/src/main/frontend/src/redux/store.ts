import { configureStore } from "@reduxjs/toolkit";
import Common from "./common";

const store = configureStore({
  reducer: {
    common: Common,
  },
});

export default store;
