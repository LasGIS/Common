import styled from 'styled-components';
import { Button } from 'antd';
import { StyledDockLayout } from '@/style/Layout/style';

export const StyledDockStartWindowLayout = styled(StyledDockLayout)`
  .dock-panel.dock-style-START_WINDOW {
    border: none;

    .dock {
      border: none;
    }

    .dock-bar.drag-initiator {
      display: none;
    }
  }
`;

export const StartWindowWrapper = styled.div`
  width: 100%;
  height: 100%;
  padding: 6px;
`;

export const SColumn = styled.div`
  height: 100%;
  position: relative;
  border: 1px solid ${({ theme }) => theme.token.borderPrimary};
  border-radius: 2px;
`;

export const SButton = styled(Button)`
  font-weight: 500;
  height: 26px;
  min-width: 26px;
`;

export const SIconButton = styled(SButton)`
  width: 28px !important;
`;

export const SFormItemWrapper = styled.div`
  position: relative;

  & > * {
    width: 100%;
  }

  & label {
    word-break: break-all;
  }

  & .ant-form-item-control {
    padding-right: 44px;
  }

  ${SIconButton} {
    position: absolute;
    right: 0;
    bottom: 0;
  }
`;
