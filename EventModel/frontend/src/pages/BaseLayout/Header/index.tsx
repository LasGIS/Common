import { Divider, Layout, Space } from 'antd';
import styled from 'styled-components';
import Navigation from '@pages/BaseLayout/Header/controls/navigation/Navigation.tsx';
import LangControl from '@pages/BaseLayout/Header/controls/LangControl';
import NavigationButtonsControl from '@pages/BaseLayout/Header/controls/NavigationButtonsControl.tsx';

const LayoutHeader = styled(Layout.Header)`
  height: 42px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  line-height: 1em;
  border-bottom: 1px solid #e5e5e5;
  background: #f5faff;
  box-shadow: 2px 2px 10px 0 #000000a1;
`;

const StyledDivider = styled(Divider)`
  height: 30px;
  background-color: #bbc9de;
`;

const Header = () => {
  return (
    <LayoutHeader>
      <Space style={{ gap: 4 }}>
        <Navigation />
        <StyledDivider type="vertical" />
        <NavigationButtonsControl />
      </Space>
      <Space style={{ gap: 4 }}>
        <LangControl />
      </Space>
    </LayoutHeader>
  );
};

export default Header;
