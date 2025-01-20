import { useAppDispatch } from '@/redux';
import { LangType } from '@/types/redux/SettingsTypes.ts';
import { setLang } from '@/redux/reducer/SettingsReducer.ts';
import { useEffect } from 'react';

export function useLanguageChanger() {
  const dispatch = useAppDispatch();

  useEffect(() => {
    setupLang((localStorage.getItem('language') as LangType) || LangType.RU);
  }, []);

  const setupLang = (lang: LangType) => {
    localStorage.setItem('language', lang);
    dispatch(setLang(lang));
  };

  /**
   * Change language on the backend
   * @param lang new language
   */
  const changeLanguage = (lang: LangType) => {
    setupLang(lang);
  };

  return (lang: LangType): void => {
    changeLanguage(lang);
  };
}
