/*
 *  @(#)actions.ts  last: 15.06.2023
 *
 * Title: LG prototype for spring-security + spring-data + react
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

import { ServiceUserDto } from "../../../common/types/dto/login-types";
import { fetchPerformLogin } from "./service";
import { clearCurrentUser, setCurrentUser } from "./sessionStorage";
import { ErrorDto } from "../../../common/types/util-types";

export const performLogin = (username: string, password: string): Promise<ServiceUserDto> => {
  const formData = new FormData();
  formData.append('username', username);
  formData.append('password', password);

  return fetchPerformLogin(formData)
    .then((user: ServiceUserDto) => {
      setCurrentUser(user);
      return Promise.resolve(user);
    })
    .catch((error: ErrorDto) => {
      clearCurrentUser();
      return Promise.reject(error);
    });
};
