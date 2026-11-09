import { MainContent } from '@/style/style';
import { Button } from 'antd';
import styled from 'styled-components';

export const RcDockContent = styled(MainContent)`
  padding: 0;
  height: calc(100vh - 68px);
  background-color: #bbc9de;
`;

export const ControlPanel = styled.div`

  flex: 0 0 42px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 5px 0px;
  height: calc(100vh - 78px);
  border-right: 1px solid rgb(140, 162, 193);
  background: rgb(245, 250, 255);
`;

export const RcDockButtonGroup = styled.div`
  text-align: center;
  display: flex;
  flex-direction: column;
  gap: 4px;
`;

export const RcDockButton = styled(Button)`
  // padding: 8px 16px;
  cursor: pointer;
`;

export const RcDockLayout = styled.div`
  flex-grow: 1;
  position: relative;
  & .dock-panel {
    &.dock-style-workspace {
      background: #EEEEFF;
    }
    &.dock-style-toolsBox {
      background: #EEFFEE;
    }
  }
`;
