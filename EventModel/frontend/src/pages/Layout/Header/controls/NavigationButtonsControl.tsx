import React from 'react';
import { Space } from 'antd';
import { ReactComponent as HomeIcon } from '@/assets/header/home.svg';
import { ReactComponent as DemoIcon } from '@/assets/header/sync_alt.svg';
import { ReactComponent as AboutIcon } from '@/assets/header/search.svg';
import { useTranslation } from 'react-i18next';
import { ButtonToolbar } from '../styles.tsx';
import { StyledTooltip } from '@pages/Layout/Header/components/StyledTooltip.tsx';
import { RouteType, useRoute } from '@/router/RouteProvider.tsx';

const NavigationButtonsControl = () => {
  const [t] = useTranslation();
  const { setRoute } = useRoute();

  return (
    <Space style={{ gap: 4 }}>
      <StyledTooltip title={t('header.menu.main')}>
        <ButtonToolbar type="text" icon={<HomeIcon />} onClick={() => setRoute(RouteType.MAIN_PAGE)} />
      </StyledTooltip>
      <StyledTooltip title={t('header.menu.demo')}>
        <ButtonToolbar type="text" icon={<DemoIcon />} onClick={() => setRoute(RouteType.DEMO_PAGE)} />
      </StyledTooltip>
      <StyledTooltip title={t('header.menu.about')}>
        <ButtonToolbar type="text" icon={<AboutIcon />} onClick={() => setRoute(RouteType.NO_MATCH_PAGE)} />
      </StyledTooltip>
    </Space>
  );
};

export default NavigationButtonsControl;
