import { useEffect } from 'react';
import type { i18n } from 'i18next';
import { useSelector } from 'react-redux';
import { langSelector } from '@/redux/reducer/SettingsReducer.ts';

export const useLanguageDetector = (i18nInstance: i18n) => {
  const lang = useSelector(langSelector);

  useEffect(() => {
    i18nInstance.changeLanguage(lang);
  }, [i18nInstance, lang]);
};
