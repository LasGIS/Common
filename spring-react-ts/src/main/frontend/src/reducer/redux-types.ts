import { RouterState } from 'connected-react-router';
import { ErrorDto } from '../types/types';

export type AppSettingsConfig = {
  name?: string;
  version?: string;
};

export interface CommonStoreData {
  loading: boolean;
  settings?: AppSettingsConfig;
  errors?: ErrorDto[];
}

export type RootStoreData = {
  router: RouterState;
  common: CommonStoreData;
};
