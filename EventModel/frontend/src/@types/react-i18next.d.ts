import 'react-i18next';
import translation from '@public/translation/en.json';

declare module 'react-i18next' {
  interface CustomTypeOptions {
    resources: {
      translation: typeof translation;
    };
  }
}
