import 'react-i18next';
import translation from '@public/locales/translation/eng.json';

declare module 'react-i18next' {
  interface CustomTypeOptions {
    // Убрать, если неймспейса "translation" больше не будет
    defaultNS: 'translation';
    resources: {
      translation: typeof translation;
    };
  }
}
