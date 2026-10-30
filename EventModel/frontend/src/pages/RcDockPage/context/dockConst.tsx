import { LayoutBase, TabData } from 'rc-dock';
import { ComponentType, LayoutPanelType } from '../types';
import { LayoutGroupType } from '../types/LayoutGroupType';

export const modelingLayoutData: LayoutBase = {
  dockbox: {
    id: LayoutPanelType.MAIN_BOX_PANEL,
    mode: 'vertical',
    children: [
      {
        id: LayoutPanelType.WORKSPACE_PANEL,
        size: 200,
        tabs: [
          {
            id: ComponentType.MODEL_MODELING_TABLES,
            group: LayoutGroupType.MODEL_WORKSPACE,
            closable: true,
          } as TabData,
          {
            id: ComponentType.MODEL_MODELING_OBJECTS,
            group: LayoutGroupType.MODEL_WORKSPACE,
            closable: true,
          } as TabData,
          {
            id: ComponentType.MODEL_MODELING_SCHEME,
            group: LayoutGroupType.MODEL_WORKSPACE,
            closable: true,
          } as TabData,
        ],
        group: LayoutGroupType.MODEL_WORKSPACE,
      },
      {
        id: LayoutPanelType.TOOLSBOX_PANEL,
        mode: 'horizontal',
        size: 70,
        children: [
          {
            id: LayoutPanelType.MODEL_RUN_LOG_PANEL,
            tabs: [
              { id: ComponentType.MODEL_RUN_LOG, group: LayoutGroupType.MODEL_TOOLSBOX, closable: true } as TabData,
            ],
            group: LayoutGroupType.MODEL_TOOLSBOX,
          },
          {
            id: LayoutPanelType.MODEL_STATISTICS_PANEL,
            tabs: [
              { id: ComponentType.MODEL_STATISTICS, group: LayoutGroupType.MODEL_TOOLSBOX, closable: true } as TabData,
            ],
            group: LayoutGroupType.MODEL_TOOLSBOX,
          },
        ],
        group: LayoutGroupType.MODEL_TOOLSBOX,
      },
    ],
  },
  floatbox: {
    mode: 'float',
    children: [],
  },
};
