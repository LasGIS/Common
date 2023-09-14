import { RequestState } from '../../../types';

export type UserRoleType = 'ADMIN' | 'CHIEF' | 'OPERATOR' | 'SUPERVISOR';
export const UserRoleTypeOption = [
  { code: 'UNDEF', name: ' ' },
  { code: 'ADMIN', name: 'Администратор' },
  { code: 'CHIEF', name: 'Начальник' },
  { code: 'OPERATOR', name: 'Оператор' },
  { code: 'SUPERVISOR', name: 'Старший смены' },
];

export type UserType = {
  userId: number;
  login: string;
  name: string;
  password: string;
  roles: UserRoleType[];
  archived: boolean;
};

export interface UserStoreData {
  searchValue: string;
  allUsers: UserType[];
  currentUser?: UserType;
  isCurrentUserShow: boolean;
  isNewUser: boolean;

  usersRequestState: RequestState;
  getUserRequestState: RequestState;
  insertUserRequestState: RequestState;
  updateUserRequestState: RequestState;
  deleteUserRequestState: RequestState;
}
