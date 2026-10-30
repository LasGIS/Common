import { Translation } from 'react-i18next';
import { TabBase, TabData } from 'rc-dock';
import { LayoutGroupType } from '../types/LayoutGroupType';
import { ComponentType } from '../types';
import ModelModelingTablesWindow from '@/components/Window/ModelModelingTablesWindow';
import ModelModelingObjectsWindow from '@/components/Window/ModelModelingObjectsWindow/ModelModelingObjectsWindow';
import ModelModelingSchemeWindow from '@/components/Window/ModelModelingSchemeWindow/ModelModelingSchemeWindow';
import ModelStatisticsWindow from '@/components/Window/ModelStatisticsWindow';
import ModelRunLogWindow from '@/components/Window/ModelRunLogWindow';

export type EmptyObject = Record<string, never>;

export interface TabsRegistry<ExtraData> {
  initializeTab(tabData: TabBase, extraData: ExtraData): TabData | undefined;
}

type TabFactory<ExtraData> = (data: TabBase, extraData: ExtraData) => TabData;

class TabsManager<ExtraData> implements TabsRegistry<ExtraData> {
  private factories: Record<string, TabFactory<ExtraData>> = {};

  public registerTab(tabId: string, factory: TabFactory<ExtraData>): this {
    this.factories[tabId] = factory;
    return this;
  }

  public initializeTab(tabData: TabBase, extraData: ExtraData): TabData | undefined {
    const id = tabData.id;
    return id ? this.factories[id]?.(tabData, extraData) : undefined;
  }
}

export const tabsRegistry: TabsRegistry<EmptyObject> = new TabsManager<EmptyObject>()
  .registerTab(ComponentType.MODEL_RUN_LOG, (data) => ({
    ...data,
    closable: true,
    group: LayoutGroupType.MODEL_TOOLSBOX,
    title: <Translation>{(t) => t('component.header.model.report')}</Translation>,
    content: <ModelRunLogWindow />,
  }))
  .registerTab(ComponentType.MODEL_STATISTICS, (data) => ({
    ...data,
    closable: true,
    group: LayoutGroupType.MODEL_TOOLSBOX,
    title: <Translation>{(t) => t('component.header.model.statistics')}</Translation>,
    content: <ModelStatisticsWindow />,
  }))
  .registerTab(ComponentType.MODEL_MODELING_TABLES, (data) => ({
    ...data,
    closable: true,
    group: LayoutGroupType.MODEL_WORKSPACE,
    title: <Translation>{(t) => t('component.header.model.modeling.tables')}</Translation>,
    content: <ModelModelingTablesWindow />,
  }))
  .registerTab(ComponentType.MODEL_MODELING_OBJECTS, (data) => ({
    ...data,
    closable: true,
    group: LayoutGroupType.MODEL_WORKSPACE,
    title: <Translation>{(t) => t('component.header.model.modeling.objects')}</Translation>,
    content: <ModelModelingObjectsWindow />,
  }))
  .registerTab(ComponentType.MODEL_MODELING_SCHEME, (data) => ({
    ...data,
    closable: true,
    group: LayoutGroupType.MODEL_WORKSPACE,
    title: <Translation>{(t) => t('component.header.model.modeling.scheme')}</Translation>,
    content: <ModelModelingSchemeWindow />,
  }));
