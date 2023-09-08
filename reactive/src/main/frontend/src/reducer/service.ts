import axios, { AxiosResponse } from 'axios';
import { AppSettingsConfig } from './redux-types';

export const axiosApi = axios.create({
  baseURL: `api/v2.0`,
  timeout: 1000,
  headers: { 'Content-Type': 'application/json' },
});

interface User {
  id: number;
  firstName: string;
  lastName: string;
}

export const fetchAppSettings = (): Promise<AxiosResponse<AppSettingsConfig>> => axiosApi.get(`/settings`);
export const hello = (): Promise<AxiosResponse<string>> => axiosApi.get(`/hello`);
export const getUser = (userId: number): Promise<AxiosResponse<User>> => axiosApi.get(`/user/` + userId);
export const createUser = (firstName: string, lastName: string): Promise<AxiosResponse<number>> =>
  axiosApi.post(`/user/` + firstName + '/' + lastName);
export const getSecured = (user: string, password: string): Promise<AxiosResponse<string>> =>
  axiosApi.get(`/secured/`, { auth: { username: user, password: password } });
