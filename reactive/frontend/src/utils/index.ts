import { ErrorDto, SystemErrorType } from '../types';

export const DateFormat = 'DD.MM.YYYY';
export const DateJsonFormat = 'YYYY-MM-DD';
export const DateTimeFormat = 'DD.MM.YYYY HH:mm';
export const UTCJsonFormat = 'YYYY-MM-DDTHH:mm:ss.SSSZZ';

export type ResultCompare = -1 | 0 | 1;

export const compareOnNull = (a?: never, b?: never, callOnEqual?: () => ResultCompare): ResultCompare => {
  if (a && !b) return 1;
  if (b && !a) return -1;
  if (!b && !a) return 0;
  return callOnEqual ? callOnEqual() : 0;
};

export const compareAlphabetically = (a?: string, b?: string, callOnEqual?: () => ResultCompare): ResultCompare => {
  const aText = a || '';
  const bText = b || '';
  if (aText < bText) return -1;
  if (aText > bText) return 1;
  return callOnEqual ? callOnEqual() : 0;
};

export const compareNumber = (a?: number, b?: number, callOnEqual?: () => ResultCompare): ResultCompare => {
  const aNum = a || 0;
  const bNum = b || 0;
  if (aNum < bNum) return -1;
  if (aNum > bNum) return 1;
  return callOnEqual ? callOnEqual() : 0;
};

export const compareBoolean = (a?: boolean, b?: boolean, callOnEqual?: () => ResultCompare): ResultCompare => {
  const aBool = a ?? false;
  const bBool = b ?? false;
  if (aBool < bBool) return -1;
  if (aBool > bBool) return 1;
  return callOnEqual ? callOnEqual() : 0;
};

// // eslint-disable-next-line @typescript-eslint/no-explicit-any
// const toArrayErrors = (error: any): ErrorDto[] => {
//   if (error.constructor === Array) {
//     // eslint-disable-next-line @typescript-eslint/no-explicit-any
//     return error.map((err: any) => toErrorDto(err));
//   } else {
//     return [toErrorDto(error)];
//   }
// };

export const checkIfResponseOk = (response: Response): boolean => response.ok && response.status >= 200 && response.status < 300;

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const checkIfResultIsError = (result: ErrorDto | any): result is ErrorDto =>
  Boolean(typeof result?.code === 'string' && typeof result?.message === 'string');

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const checkIfResultIsSystemError = (result: SystemErrorType | any): result is SystemErrorType =>
  Boolean(typeof result?.error === 'string' && typeof result?.path === 'string' && typeof result?.status === 'number');

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const toErrorDto = (error: any | ErrorDto | SystemErrorType): ErrorDto => {
  if (checkIfResultIsError(error)) {
    return error;
  } else if (checkIfResultIsSystemError(error)) {
    return {
      code: error.error?.toUpperCase(),
      message: `${error.status} ${error.error} from ${error.path}`,
    };
  } else {
    const message: string = typeof error === 'object' ? JSON.stringify(error) : error.toString();
    return { code: 'UNDEF', message };
  }
};
