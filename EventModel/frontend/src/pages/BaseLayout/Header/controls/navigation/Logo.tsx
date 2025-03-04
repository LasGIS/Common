import styled from 'styled-components';
import { Space } from 'antd';
import { ReactComponent as LogoSvg } from '@/assets/vite.svg';
import { ReactComponent as NameLogoEnSvg } from '@/assets/logo/EventModel.svg';
import { ReactComponent as NameLogoRuSvg } from '@/assets/logo/EventModel_ru.svg';
import { ReactComponent as ChevronSvg } from '@/assets/logo/Chevron.svg';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { langSelector } from '@/redux/reducer/SettingsReducer.ts';
import { LangType } from '@/types/redux/SettingsTypes.ts';
import { JSX } from 'react';

const StyledChevronSvg = styled(ChevronSvg)`
  color: #1c4682;
`;
const StyledLogoSvg = styled(LogoSvg)`
  color: #1c4682;
`;

const StyledSpace = styled(Space)`
  display: flex;
  gap: 10px;
  align-items: center;
  cursor: pointer;
`;

export const Logo = () => {
  const navigate = useNavigate();
  const lang = useSelector(langSelector);

  const logoSvg = (): JSX.Element => (lang == LangType.EN ? <NameLogoEnSvg /> : <NameLogoRuSvg />);

  return (
    <StyledSpace onClick={() => navigate('/')}>
      {logoSvg()}
      <StyledLogoSvg />
      <StyledChevronSvg />
    </StyledSpace>
  );
};
