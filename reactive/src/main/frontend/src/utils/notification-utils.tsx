import * as React from 'react';
import { notification, Modal } from 'antd';
import { IconType } from 'antd/lib/notification/interface';
import { ErrorDto } from '../types';

const { confirm } = Modal;

const supportUrl = '';
const MESSAGE_ERROR: { [key: string]: React.ReactNode } = {
  BAD_REQUEST: 'Некорректный запрос',
  UNAUTHORIZED: 'Неправильные имя пользователя или пароль',
  FORBIDDEN: 'В доступе отказано',
  NOT_FOUND: 'Ресурс не найден',
  METHOD_NOT_ALLOWED: 'Method Not Allowed',
  NOT_ACCEPTABLE: 'Not Acceptable',
  CONFLICT: 'Conflicting Request',
  GONE: 'Ресурс больше недоступен',
  UNSUPPORTED_MEDIA_TYPE: 'Unsupported Media Type',
  TOO_MANY_REQUESTS: 'Too Many Requests',
  UNPROCESSABLE_ENTITY: 'Unprocessable Entity',
  INTERNAL_SERVER_ERROR: (
    <div>
      Непредвиденная ошибка на сервере, обратитесь в{' '}
      <a href={supportUrl} target="_blank" rel="noreferrer">
        службу поддержки
      </a>
    </div>
  ),
  NOT_IMPLEMENTED: 'Not Implemented',
  BAD_GATEWAY: 'Ошибка на прокси сервере',
  SERVICE_UNAVAILABLE: (
    <div>
      Сервис недоступен, попробуйте повторить запрос через 10 минут.
      <br />
      Если проблема повторяется - обратитесь в{' '}
      <a href={supportUrl} target="_blank" rel="noreferrer">
        службу поддержки
      </a>
    </div>
  ),
  GATEWAY_TIMEOUT: 'Превышено время ожидания шлюза',
};

export const showNotification = (message: React.ReactNode, detail?: React.ReactNode, type?: IconType, optional?: object) => {
  notification.open({
    type: type ? type : 'info',
    message: message,
    description: detail,
    duration: 20,
    ...optional,
  });
};

export const showError = (error: ErrorDto) => {
  let message: React.ReactNode = MESSAGE_ERROR[error.code];
  let detail: string | undefined;
  if (message) {
    detail = error.message;
  } else {
    message = error.message;
    detail = error.detail;
  }
  showNotification(message, detail, 'error');
};

export const showErrors = (errors: ErrorDto[]) => {
  errors.forEach((error) => {
    showError(error);
  });
};

export const showConfirm = (message: React.ReactNode, callBack: () => void) => {
  confirm({
    content: message,
    okText: 'Хорошо',
    onOk: callBack,
  });
};

export const clearErrors = () => {
  notification.destroy();
};
