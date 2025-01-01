import { createInstance } from 'i18next';
import I18NextHttpBackend from 'i18next-http-backend';

const i18nInstance = createInstance({
  backend: {
    loadPath: '/locales/{{ns}}/{{lng}}.json',
    requestOptions: {
      cache: 'no-store',
    },
  },
  fallbackLng: 'ru',
  interpolation: {
    escapeValue: false,
  },
  defaultNS: 'translation',
  ns: ['translation'],
  debug: process.env.NODE_ENV === 'development',
});

i18nInstance.use(I18NextHttpBackend).init();

export default i18nInstance;
