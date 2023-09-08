import axios, { AxiosResponse } from 'axios';
import { AppSettingsConfig, AuthUser } from './redux-types';

export const axiosApi = axios.create({
  baseURL: `/api/v2.0/`,
  timeout: 1000,
  headers: { 'Content-Type': 'application/json' },
});

export default {
  fetchAppSettings(): Promise<AxiosResponse<AppSettingsConfig>> {
    return axiosApi.get(`/settings`);
  },
  requestGetAuthUser(): Promise<AxiosResponse<AuthUser>> {
    return axiosApi.get(`/auth/user`);
  },
};
