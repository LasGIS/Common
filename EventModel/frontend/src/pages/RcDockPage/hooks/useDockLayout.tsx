import DockLayout, { TabBase, TabData } from 'rc-dock';
import { ComponentType, LayoutGroupType, SearchResult } from '../types';
import { Filter } from 'rc-dock/lib/Algorithm';
import { tabsRegistry } from '../components/TabsRegistry';

const useDockLayout = (dockRef: React.RefObject<DockLayout | null>) => {
  const loadTab = (data: TabBase): TabData => {
    if ('content' in data) {
      return data as TabData;
    }
    return (
      tabsRegistry.initializeTab(data, {}) ?? {
        id: data.id,
        title: 'Unknown',
        content: <div>Unknown tab with id &quot;{data.id}&quot;</div>,
      }
    );
  };

  const isOpenedWindow = (id: string): boolean => {
    return !!dockRef.current?.find(id);
  };

  const setWindowActive = (id: string) => {
    dockRef.current?.updateTab(id, null, true);
  };

  const findSiblingPanel = (group: LayoutGroupType): { siblingTab: SearchResult; panel: SearchResult } => {
    const siblingTab = dockRef.current?.find((item) => {
      return 'group' in item && item.group === group;
    }, Filter.AnyTab);
    const panel =
      siblingTab && siblingTab.parent
        ? siblingTab.parent
        : dockRef.current?.find((item) => {
            return 'group' in item && item.group === group;
          }, Filter.AnyPanel);
    return { siblingTab, panel };
  };

  const addToWorkspace = (id: ComponentType) => {
    if (dockRef.current) {
      if (isOpenedWindow(id)) {
        setWindowActive(id);
      } else {
        const tabData: TabData = loadTab({ id });
        const { siblingTab, panel } = findSiblingPanel(LayoutGroupType.MODEL_WORKSPACE);
        dockRef.current.dockMove(tabData, panel ? panel : siblingTab || null, 'middle');
      }
    }
  };

  const addToToolsBox = (id: ComponentType) => {
    if (dockRef.current) {
      if (isOpenedWindow(id)) {
        setWindowActive(id);
      } else {
        const tabData: TabData = loadTab({ id });
        const { siblingTab, panel } = findSiblingPanel(LayoutGroupType.MODEL_TOOLSBOX);
        dockRef.current.dockMove(tabData, panel ? panel : siblingTab || null, 'left');
      }
    }
  };

  return { loadTab, isOpenedWindow, setWindowActive, addToWorkspace, addToToolsBox };
};

export default useDockLayout;
