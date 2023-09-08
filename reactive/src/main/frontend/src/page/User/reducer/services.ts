import { UserType } from './types';
import { AxiosResponse } from 'axios';
import { axiosApi } from '../../../reducer/service';

export default {
  /** Get all users from backend */
  requestGetAllUsers(): Promise<AxiosResponse<UserType[]>> {
    return axiosApi.get(`/user`);
  },
  /** Get user by userId */
  requestGetUserById(userId: number): Promise<AxiosResponse<UserType>> {
    return axiosApi.get(`/user/${userId}`);
  },
  /** Get user by login */
  requestGetUserByLogin(login: string): Promise<AxiosResponse<UserType>> {
    return axiosApi.get(`/user/login`, { params: { login } });
  },
  /** Insert new user to backend */
  requestCreateNewUser(user: UserType): Promise<AxiosResponse<UserType>> {
    return axiosApi.post(`/user`, user);
  },
  /** Update User in backend */
  requestUpdateUser(user: UserType): Promise<AxiosResponse<UserType>> {
    return axiosApi.put(`/user/${user.userId}`, user);
  },
  /** Delete User from backend */
  requestDeleteUser(userId: number): Promise<AxiosResponse<number>> {
    return axiosApi.delete(`/user/${userId}`);
  },
};
