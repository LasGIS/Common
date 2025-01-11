import 'i18next';
import translation from '@public/locales/translation/eng.json';

declare module 'i18next' {
  interface CustomTypeOptions {
    defaultNS: 'translation';
    resources: {
      translation: typeof translation;
    };
  }
}
