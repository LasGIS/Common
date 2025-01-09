import styled from 'styled-components';
import { Space } from 'antd';
import { ReactComponent as LogoSvg } from '@/assets/logo/Logo.svg';
import { ReactComponent as NameLogoSvg } from '@/assets/logo/EventModel.svg';
import { ReactComponent as ChevronSvg } from '@/assets/logo/Chevron.svg';
import { RouteType, useRoute } from '@/router/RouteProvider.tsx';

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
  const { setRoute } = useRoute();

  return (
    <StyledSpace onClick={() => setRoute(RouteType.MAIN_PAGE)}>
      <NameLogoSvg />
      <StyledLogoSvg />
      <StyledChevronSvg />
    </StyledSpace>
  );
};
