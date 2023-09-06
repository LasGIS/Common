import axios, { AxiosResponse } from 'axios';
import { AppSettingsConfig } from './redux-types';

const axiosApi = axios.create({
  baseURL: `/v1.0`,
  timeout: 1000,
  headers: { 'Content-Type': 'application/json' },
});

interface User {
  id: number;
  firstName: string;
  lastName: string;
}

export default {
  fetchAppSettings(): Promise<AxiosResponse<AppSettingsConfig>> {
    return axiosApi.get(`/settings`);
  },

  hello(): Promise<AxiosResponse<string>> {
    return axiosApi.get(`/hello`);
  },
  getUser(userId: number): Promise<AxiosResponse<User>> {
    return axiosApi.get(`/user/` + userId);
  },
  createUser(firstName: string, lastName: string): Promise<AxiosResponse<number>> {
    return axiosApi.post(`/user/` + firstName + '/' + lastName);
  },
  getSecured(user: string, password: string): Promise<AxiosResponse<string>> {
    return axiosApi.get(`/secured/`, {
      auth: {
        username: user,
        password: password,
      },
    });
  },
};
