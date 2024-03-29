import type { PayloadAction } from '@reduxjs/toolkit';
import { createAsyncThunk, createSelector, createSlice } from '@reduxjs/toolkit';
import type { AppSettingsConfig, AuthUser, CommonStoreData } from './redux-types';
import { RootStoreData } from './redux-types';
import api from './service';
import type { ErrorDto } from '../types';
import type { AppDispatch } from './store';
import { AxiosResponse } from 'axios';

export const getAppSettings = createAsyncThunk<AppSettingsConfig, void, { dispatch: AppDispatch; state: CommonStoreData }>(
  'COMMON_GET_SYSTEM_SETTINGS',
  (v, thunkApi) => {
    thunkApi.dispatch(commonShowLoader());
    return api
      .fetchAppSettings()
      .then((response: AxiosResponse<AppSettingsConfig>) => {
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        return thunkApi.rejectWithValue(error);
      })
      .finally(() => thunkApi.dispatch(commonHideLoader()));
  },
);

export const getAuthUser = createAsyncThunk<AuthUser, void, { dispatch: AppDispatch; state: CommonStoreData }>(
  'COMMON_GET_AUTH_USER',
  (v, thunkApi) => {
    thunkApi.dispatch(commonShowLoader());
    return api
      .requestGetAuthUser()
      .then((response: AxiosResponse<AuthUser>) => {
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        return thunkApi.rejectWithValue(error);
      })
      .finally(() => thunkApi.dispatch(commonHideLoader()));
  },
);

// Define the initial state using that type
const initialState: CommonStoreData = {
  loading: false,
  settings: undefined,
  authUser: { firstName: 'Соломон', lastName: 'Израильевич' },
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
      })

      /* getAppSettings (COMMON_GET_AUTH_USER) */
      .addCase(getAuthUser.pending.type, (state: CommonStoreData) => {
        state.authUser = undefined;
      })
      .addCase(getAuthUser.fulfilled.type, (state: CommonStoreData, action: PayloadAction<AuthUser>) => {
        state.authUser = action.payload;
      })
      .addCase(getAuthUser.rejected.type, (state: CommonStoreData, action: PayloadAction<ErrorDto[]>) => {
        state.errors = action.payload;
      });
  },
});

export const commonRootSelector = (root: RootStoreData) => root.common;
export const commonLoadingSelector = createSelector(commonRootSelector, (state) => state?.loading);
export const commonSettingsSelector = createSelector(commonRootSelector, (state) => state?.settings);
export const commonAuthUserSelector = createSelector(commonRootSelector, (state) => state?.authUser);
export const commonConnectedRouterSelector = (root: RootStoreData) => (root && root.router) || null;

export const { commonShowLoader, commonHideLoader } = commonSlice.actions;

export default commonSlice.reducer;
