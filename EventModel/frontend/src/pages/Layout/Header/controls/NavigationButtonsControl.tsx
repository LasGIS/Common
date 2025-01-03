import React from 'react';
import { Space } from 'antd';
import { ReactComponent as HomeIcon } from '@/assets/header/home.svg';
import { ReactComponent as DemoIcon } from '@/assets/header/sync_alt.svg';
import { ReactComponent as AboutIcon } from '@/assets/header/search.svg';
import { useTranslation } from 'react-i18next';
import { ButtonToolbar } from '../styles.tsx';
import { useNavigate } from 'react-router-dom';
import { StyledTooltip } from '@pages/Layout/Header/components/StyledTooltip.tsx';

const NavigationButtonsControl = () => {
  const [t] = useTranslation();
  const navigate = useNavigate();

  return (
    <Space style={{ gap: 4 }}>
      <StyledTooltip title={t('header.menu.main')}>
        <ButtonToolbar type="text" icon={<HomeIcon />} onClick={() => navigate('/')} />
      </StyledTooltip>
      <StyledTooltip title={t('header.menu.demo')}>
        <ButtonToolbar type="text" icon={<DemoIcon />} onClick={() => navigate('/demo')} />
      </StyledTooltip>
      <StyledTooltip title={t('header.menu.about')}>
        <ButtonToolbar type="text" icon={<AboutIcon />} onClick={() => navigate('/about')} />
      </StyledTooltip>
    </Space>
  );
};

export default NavigationButtonsControl;
