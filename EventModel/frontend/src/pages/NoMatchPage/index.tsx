import React from 'react';
import { MainContent } from '@/style/style.tsx';
import { useTranslation } from 'react-i18next';

const NoMatchPage: React.FC = () => {
  const { t } = useTranslation();

  return (
    <MainContent>
      {t('nomatch.title')}: <b>{window.location.href}</b>
    </MainContent>
  );
};

export default NoMatchPage;
