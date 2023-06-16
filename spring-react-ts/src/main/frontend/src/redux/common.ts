import { createAsyncThunk, createSelector, createSlice, PayloadAction } from "@reduxjs/toolkit";
import { AppSettingsConfig, CommonStoreData } from "./types";
import { fetchAppSettings } from "./service";
import { ErrorDto } from "../types/types";
import { AppDispatch } from "../../redux/store";
import { RootStoreData } from "./redux-types";

export const getAppSettings = createAsyncThunk<any, void, { dispatch: AppDispatch; state: CommonStoreData }>(
  'COMMON_GET_SYSTEM_SETTINGS',
  (vd, { rejectWithValue }) => {
    return fetchAppSettings()
      .then((data: AppSettingsConfig) => {
        return data;
      })
      .catch((error: ErrorDto[]) => {
        console.log(error);
        return rejectWithValue(error);
      });
  },
);

const commonSlice = createSlice({
  name: 'COMMON',
  initialState: {
    loading: false,
    settings: undefined,
    errors: undefined,
  },
  reducers: {
    commonShowLoader(state: CommonStoreData) {
      state.loading = true;
    },
    commonHideLoader(state: CommonStoreData) {
      state.loading = false;
    },
    setEnvironment(state: CommonStoreData, action: PayloadAction<Environment>) {
      state.currentEnvironment = action.payload;
    },
    getSystemSettings(state: CommonStoreData, action: PayloadAction<AppSettingsConfig>) {
      state.settings = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      /* getAppSettings (COMMON_GET_SYSTEM_SETTINGS) */
      .addCase(getAppSettings.pending.type, (state: CommonStoreData) => {
        state.settings = undefined;
      })
      .addCase(getAppSettings.fulfilled.type, (state: CommonStoreData, action: PayloadAction<AppSettingsConfig>) => {
        state.settings = action.payload;
      })
      .addCase(getAppSettings.rejected.type, (state: CommonStoreData, action: PayloadAction<ErrorDto[]>) => {
        state.errors = action.payload;
      });
  },
});

export const commonRootSelector = (root: RootStoreData) => root.common;

export const currentEnvironmentSelector = createSelector(commonRootSelector, (state) => state && state.currentEnvironment);
export const commonLoadingSelector = createSelector(commonRootSelector, (state) => state && state.loading);
export const monitoringReadCorsTimeoutSelector = createSelector(
  commonRootSelector,
  (state) => (state && state.settings && state.settings.monitoring && state.settings.monitoring.readCorsTimeout) || 1000,
);

/** 'connected-react-router' */
export const commonConnectedRouterSelector = (root: RootStoreData) => (root && root.router) || null;

export const { commonShowLoader, commonHideLoader, setEnvironment, getSystemSettings } = commonSlice.actions;

export default commonSlice.reducer;
