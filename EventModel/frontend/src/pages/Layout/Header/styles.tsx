import styled from 'styled-components';
import { Button } from 'antd';

export const ButtonToolbar = styled(Button)`
  &.ant-btn.ant-btn-text:not(:disabled) {
    color: #1c4682;
  }

  &.ant-btn.ant-btn-text:disabled {
    color: #b8d5ee;
  }

  &.ant-btn.ant-btn-text:not(:disabled):hover {
    background: #d4e6f6;
  }
`;
