import type { PropsWithChildren } from 'react';
import React from 'react';
import { I18nextProvider } from 'react-i18next';
import i18nInstance, { useLanguageDetector } from '@/i18n';

const Providers: React.FC<PropsWithChildren> = ({ children }) => {
  useLanguageDetector(i18nInstance);
  return <I18nextProvider i18n={i18nInstance}>{children}</I18nextProvider>;
};

export default Providers;
