import { UserType } from './types';
import { AxiosResponse } from 'axios';
import { axiosApi } from '../../../reducer/service';

/** Get all users from backend */
export const requestGetAllUsers = (): Promise<AxiosResponse<UserType[]>> => axiosApi.get(`/user`);
/** Get user by userId */
export const requestGetUserById = (userId: number): Promise<AxiosResponse<UserType>> => axiosApi.get(`/user/${userId}`);
/** Get user by login */
export const requestGetUserByLogin = (login: string): Promise<AxiosResponse<UserType>> => axiosApi.get(`/user/login`, { params: { login } });
/** Insert new user to backend */
export const requestCreateNewUser = (user: UserType): Promise<AxiosResponse<UserType>> => axiosApi.post(`/user`, user);
/** Update User in backend */
export const requestUpdateUser = (user: UserType): Promise<AxiosResponse<UserType>> => axiosApi.put(`/user/${user.userId}`, user);
/** Delete User from backend */
export const requestDeleteUser = (userId: number): Promise<AxiosResponse<number>> => axiosApi.delete(`/user/${userId}`);
