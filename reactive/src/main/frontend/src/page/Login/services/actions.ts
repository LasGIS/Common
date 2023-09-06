import { ServiceUserDto } from '../../../types/dto/login-types';
import { fetchPerformLogin } from './service';
import { clearCurrentUser, setCurrentUser } from './sessionStorage';
import { ErrorDto } from '../../../types/types';

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
