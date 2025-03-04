import 'i18next';
import translation from '@public/translation/en.json';

declare module 'i18next' {
  interface CustomTypeOptions {
    resources: {
      translation: typeof translation;
    };
  }
}
