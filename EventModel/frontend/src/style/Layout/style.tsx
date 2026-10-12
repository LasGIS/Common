import styled from 'styled-components';

export const StyledDockLayout = styled.div`
  display: flex;
  flex-direction: column;
  flex-grow: 1;

  .dock-panel {
    border-radius: 2px;
  }

  .dock-panel .dock {
    border: ${({ theme }) => `1px solid ${theme.token.windowBorderColor}`};
    border-radius: 2px;

    .dock-bar {
      background: ${({ theme }) => theme.token.windowHeaderColor};
      border-bottom: ${({ theme }) => `1px solid ${theme.token.windowHeaderBorderColor}`};
      padding: 0;
      &.drag-initiator:has(.empty-header-panel) {
        display: none;
      }
    }

    .dock-tab {
      background: transparent;
      height: auto;
      font-size: 14px;

      margin-right: 4px;
      border: 1px solid $panel-border-color;
      border-radius: 2px 2px 0 0;
      color: ${({ theme }) => theme.token.windowTextColor} !important;

      &-btn {
        background: $panel-border-color;
      }

      & > div {
        padding: 2px 16px;
      }

      &:hover {
        background-color: $component-background;
      }
    }

    .dock-tab {
      border-bottom-width: 2px;
    }

    .dock-nav-list:has(> :nth-of-type(3)) > .dock-tab-active {
      border-bottom: ${({ theme }) => `3px solid ${theme.token.colors.darkBlue}`};
      border-block-end-color: ${({ theme }) => theme.token.colors.darkBlue};
    }

    .dock-tab.dock-tab-active {
      border: none;
      color: inherit;

      &:hover {
        background-color: $component-background-active;
      }
    }

    .dock-nav-wrap {
      transform: translate(0, 1px);
      height: 26px;
    }

    .dock-ink-bar {
      display: none;
    }

    .drag-initiator {
      font-size: 12px;
    }

    .dock-nav {
      height: 26px;
    }

    .dock-tab-hit-area {
      left: -2px;
      right: -2px;
    }

    .dock-panel-max-btn,
    .dock-panel-min-btn {
      margin-top: 4px;
    }

    .dock-tab-close-btn {
      font-size: 10px;
      top: 4px;
      color: $panel-close-btn-color;
    }
  }
`;

export const StyledPanelWrapperLayout = styled.div`
  display: flex;
  flex-direction: row;
  height: 100%;
`;

export const StyledPanelContext = styled.div`
  width: 100%;
  background: white;
  padding: 20px;
`;
