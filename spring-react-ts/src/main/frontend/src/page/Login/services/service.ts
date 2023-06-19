import { Role, ServiceUserDto } from '../../../types/dto/login-types';
import { ErrorDto } from '../../../types/types';

export const fetchPerformLogin = (formData: FormData): Promise<ServiceUserDto> =>
  new Promise((resolve: (user: ServiceUserDto) => void, reject: (error: ErrorDto) => void) => {
    fetch(`/perform-login`, { method: 'POST', body: formData })
      .then((response: Response) => {
        if (response.status === 200) {
          response.json().then((user: ServiceUserDto) => {
            if (user.roles.includes(Role.ADMIN) || user.roles.includes(Role.USER)) {
              resolve(user);
            } else {
              reject({
                code: -4,
                message: `Нет прав`,
                detail: `Пользователь ${user.fullName} не имеет прав ни на редактирование, ни на просмотр!`,
              });
            }
          });
        } else if (response.status === 401) {
          reject({ code: -1, message: `Некорректный логин или пароль`, detail: '' });
        } else {
          reject({ code: -2, message: `Ошибка входа`, detail: `${response.status} - ${response.statusText}` });
        }
      })
      .catch((error) => {
        reject({ code: -3, message: `Ошибка входа`, detail: error.toString() });
      });
  });
