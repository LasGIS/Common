import styled from 'styled-components';
import { Space } from 'antd';
import { ReactComponent as LogoSvg } from '@/assets/logo/Logo_22x28.svg';
import { ReactComponent as NameLogoSvg } from '@/assets/logo/DeltaMod.svg';
import { ReactComponent as ChevronSvg } from '@/assets/logo/Chevron.svg';
import { useNavigate } from 'react-router-dom';

const StyledSpace = styled(Space)`
  display: flex;
  gap: 10px;
  align-items: center;
  cursor: pointer;
`;

export const Logo = () => {
  const navigate = useNavigate();

  return (
    <StyledSpace onClick={() => navigate('/')}>
      <NameLogoSvg />
      <LogoSvg />
      <ChevronSvg />
    </StyledSpace>
  );
};
