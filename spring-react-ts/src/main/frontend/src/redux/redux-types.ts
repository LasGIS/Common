import { RouterState } from "connected-react-router";

export type AppSettingsConfig = {
  printLocale?: boolean;
  name?: string;
  version?: string;
};

export type CommonStoreData = {
  loading?: boolean;
  settings?: AppSettingsConfig;
};

export type RootStoreData = {
  router: RouterState;
  common: CommonStoreData;
};
