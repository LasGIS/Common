import { RouterState } from 'connected-react-router';
import { ErrorDto } from '../types';
import { UserStoreData } from '../page/User/reducer/types';

export type AppSettingsConfig = {
  name?: string;
  version?: string;
};

export type AuthUser = {
  id?: string;
  username?: string;
  firstName: string;
  lastName: string;
  email?: string;
  image?: string;
  roles?: string[];
};

export interface CommonStoreData {
  loading: boolean;
  settings?: AppSettingsConfig;
  authUser?: AuthUser;
  errors?: ErrorDto[];
}

export type RootStoreData = {
  router: RouterState;
  common: CommonStoreData;
  user: UserStoreData;
};
