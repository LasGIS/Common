import type { PayloadAction } from '@reduxjs/toolkit';
import { createAsyncThunk, createSelector, createSlice } from '@reduxjs/toolkit';
import type { AppSettingsConfig, CommonStoreData } from './redux-types';
import { RootStoreData } from './redux-types';
import api from './service';
import type { ErrorDto } from '../types/types';
import type { AppDispatch, RootState } from './store';
import { AxiosResponse } from 'axios';

export const getAppSettings = createAsyncThunk<RootState, void, { dispatch: AppDispatch; state: CommonStoreData }>(
  'COMMON_GET_SYSTEM_SETTINGS',
  () => {
    return api
      .fetchAppSettings()
      .then((response: AxiosResponse<AppSettingsConfig>) => {
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        return error;
      });
  },
);

// Define the initial state using that type
const initialState: CommonStoreData = {
  loading: false,
  settings: undefined,
  errors: undefined,
};

const commonSlice = createSlice({
  name: 'COMMON',
  initialState,
  reducers: {
    commonShowLoader: (state: CommonStoreData) => {
      state.loading = true;
    },
    commonHideLoader: (state: CommonStoreData) => {
      state.loading = false;
    },
    getSystemSettings: (state: CommonStoreData, action: PayloadAction<AppSettingsConfig>) => {
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
export const commonLoadingSelector = createSelector(commonRootSelector, (state) => state && state.loading);
export const commonConnectedRouterSelector = (root: RootStoreData) => (root && root.router) || null;

export const { commonShowLoader, commonHideLoader, getSystemSettings } = commonSlice.actions;

export default commonSlice.reducer;
