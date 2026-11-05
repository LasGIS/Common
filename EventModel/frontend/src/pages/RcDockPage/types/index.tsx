import { BoxData, PanelData, TabData } from 'rc-dock';

export enum ComponentType {
  MODEL_RUN_LOG = 'modelRunLog',
  MODEL_CODE_SEARCH = 'modelCodeSearch',
  MODEL_STATISTICS = 'modelStatistics',
  MODEL_MODELING_TABLES = 'modelModelingTablesWindow',
  MODEL_MODELING_SCHEME = 'modelModelingSchemeWindow',
  MODEL_MODELING_OBJECTS = 'modelModelingObjectsWindow',
  MODEL_SUB_SCHEME = 'modelSubScheme',
}

export enum LayoutPanelType {
  MAIN_BOX_PANEL = 'mainBox',
  WORKSPACE_PANEL = 'workspacePanel',
  TOOLSBOX_PANEL = 'toolsBoxPanel',
  MODEL_RUN_LOG_PANEL = 'modelRunLogPanel',
  MODEL_STATISTICS_PANEL = 'modelStatisticsPanel',
}

export enum LayoutGroupType {
  MODEL_WORKSPACE = 'workspace',
  MODEL_TOOLSBOX = 'toolsBox',
}

export type ChangesType = TabData | PanelData | BoxData;

export type SearchResult = ChangesType | undefined;
