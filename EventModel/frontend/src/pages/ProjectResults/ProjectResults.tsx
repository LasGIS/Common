import React from 'react';
import { useTranslation } from 'react-i18next';
import { MainContent } from '@/style/style';

const ProjectResults: React.FC = () => {
  const { t } = useTranslation();

  return <MainContent>{t('header.menu.solver')}</MainContent>;
};

export default ProjectResults;
