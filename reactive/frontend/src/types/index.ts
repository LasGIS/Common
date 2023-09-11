export enum RequestState {
  UNDEFINED,
  START,
  SUCCESS,
  FAIL,
}

export type ErrorDto = {
  code: string;
  message: string;
  detail?: string;
};

export type SystemErrorType = {
  error: string;
  path: string;
  status: number;
  timestamp: string;
};
