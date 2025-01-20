import { createInstance } from 'i18next';
import I18NextHttpBackend from 'i18next-http-backend';
/*
import { TranslationTypes } from '@types/TranslationTypes.ts';
import en from '@public/translation/en.json';
import ru from '@public/translation/ru.json';

const resources: Record<string, { translation: TranslationTypes }> = {
  en: { translation: en },
  ru: { translation: ru },
};
*/

const i18nInstance = createInstance({
  backend: {
    loadPath: '/{{ns}}/{{lng}}.json',
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
