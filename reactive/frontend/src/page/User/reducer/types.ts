import { RequestState } from '../../../types';

export type UserRoleType = 'ADMIN' | 'CHIEF' | 'OPERATOR' | 'SUPERVISOR';

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
