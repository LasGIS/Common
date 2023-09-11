import { Role, ServiceUserDto } from '../../../types/dto/login-types';

export const clearCurrentUser = () => {
  sessionStorage.removeItem('current_user_name');
  sessionStorage.removeItem('current_user_full_name');
  sessionStorage.removeItem('current_user_roles');
};

export const setCurrentUser = (currentUser: ServiceUserDto) => {
  sessionStorage.setItem('current_user_name', currentUser.username);
  sessionStorage.setItem('current_user_full_name', currentUser.fullName);
  sessionStorage.setItem('current_user_roles', currentUser.roles.map((role: Role) => role.substring(5)).join(','));
};

export const getCurrentUser = (): ServiceUserDto | undefined => {
  const username: string | null = sessionStorage.getItem('current_user_name');
  const fullName: string | null = sessionStorage.getItem('current_user_full_name');
  const roles: string | null = sessionStorage.getItem('current_user_roles');

  return username && fullName && roles
    ? {
        username: username,
        fullName: fullName,
        roles: roles.split(',').map((role: string) => Role[role as keyof typeof Role]),
      }
    : undefined;
};

export const isUserAuthorized = () => {
  const roles: Role[] = getCurrentUser()?.roles || [];
  return Boolean(roles.includes(Role.ADMIN) || roles.includes(Role.USER));
};

export const isUserCannotEdit = () => {
  const roles: Role[] = getCurrentUser()?.roles || [];
  return !roles.includes(Role.ADMIN);
};
