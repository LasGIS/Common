import DockLayout, { BoxData, DropDirection, PanelData, TabBase, TabData } from 'rc-dock';
import { ComponentType, LayoutGroupType } from '../types';
import { Filter } from 'rc-dock/lib/Algorithm';
import { tabsRegistry } from '../components/TabsRegistry';

const ORDER_TOOLS_BOX_COMPONENTS: Record<string, number> = {
  [ComponentType.MODEL_RUN_LOG]: 1,
  [ComponentType.MODEL_CODE_SEARCH]: 2,
  [ComponentType.MODEL_STATISTICS]: 3,
};

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

  const findPanels = (
    group: LayoutGroupType,
    onCheck?: (panel: PanelData) => boolean
  ): {
    tab: TabData | undefined;
    panel: PanelData | undefined;
    box: BoxData | undefined;
    dockbox: BoxData | undefined;
  } => {
    const tab = dockRef.current?.find((item) => {
      return 'group' in item && item.group === group;
    }, Filter.Tab | Filter.Docked) as TabData | undefined;
    const panel = dockRef.current?.find((item) => {
      return 'group' in item && item.group === group && typeof onCheck === 'function' && onCheck(item as PanelData);
    }, Filter.Panel | Filter.Docked) as PanelData | undefined;
    const box = dockRef.current?.find(() => true, Filter.Box | Filter.Docked) as BoxData | undefined;
    const dockbox = dockRef.current?.find(() => true, Filter.Docked) as BoxData | undefined;
    return { tab, panel, box, dockbox };
  };

  const addToWorkspace = (id: ComponentType) => {
    if (dockRef.current) {
      if (isOpenedWindow(id)) {
        setWindowActive(id);
      } else {
        const tabData: TabData = loadTab({ id });
        const { tab, panel, box, dockbox } = findPanels(LayoutGroupType.MODEL_WORKSPACE);
        const parent = tab && tab.parent ? tab.parent : panel;
        if (parent) {
          dockRef.current.dockMove(tabData, parent, 'middle');
        } else if (box) {
          dockRef.current.dockMove(tabData, box, 'top');
        } else if (dockbox) {
          dockRef.current.dockMove(tabData, dockbox, 'top');
        }
      }
    }
  };

  // #####################################

  const getToolsBoxDirection = (
    id: ComponentType,
    panel: PanelData
  ): { panel: PanelData; direction: DropDirection } => {
    if (panel.parent && panel.activeId) {
      const order = ORDER_TOOLS_BOX_COMPONENTS[id];
      const panelOrder = ORDER_TOOLS_BOX_COMPONENTS[panel.activeId];
      const children = panel.parent.children as PanelData[];
      return children.reduce(
        (acc: { panel: PanelData; direction: DropDirection }, item) => {
          const itemId = item.activeId;
          const accId = acc.panel.activeId;
          if (itemId && accId) {
            const orderItem = ORDER_TOOLS_BOX_COMPONENTS[itemId];
            const orderAcc = ORDER_TOOLS_BOX_COMPONENTS[accId];
            if (orderItem && orderAcc) {
              if (orderAcc < order && orderItem < order && orderAcc < orderItem) {
                return { panel: item, direction: 'right' as DropDirection };
              } else if (orderAcc > order && orderItem > order && orderAcc > orderItem) {
                return { panel: item, direction: 'left' as DropDirection };
              }
            }
          }
          return acc;
        },
        { panel: panel, direction: order > panelOrder ? 'right' : 'left' }
      );
    }
    return { panel, direction: 'left' };
  };

  const checkOnToolsBoxValid = (panel: PanelData): boolean => {
    const parent = panel.parent;
    const isParentOutGroup = parent?.children.some(
      (item) => 'group' in item ? item.group !== LayoutGroupType.MODEL_TOOLSBOX : true
    );
    if (
      panel.tabs.length === 1 &&
      panel.tabs[0].group === LayoutGroupType.MODEL_TOOLSBOX &&
      !(parent?.children && parent?.children.length > 1 && isParentOutGroup)
    ) {
      return true;
    }
    return (
      panel.tabs.length === 1 &&
      // parent?.children.length === 1 ||
      (isParentOutGroup && parent?.mode === 'vertical') ||
      (!isParentOutGroup && parent?.mode === 'horizontal')
    );
  };

  const getBottomLeftPanelData = (node: BoxData | PanelData): { panel: BoxData | PanelData; isToolsBox: boolean } => {
    const isBoxData = 'children' in node && Array.isArray(node.children);
    if (isBoxData) {
      const nFirst = node.children.at(0);
      const nLast = node.children.at(node.children.length - 1);
      if (node.mode === 'horizontal' && nFirst) {
        const { panel, isToolsBox } = getBottomLeftPanelData(nFirst);
        console.log("node.mode === 'horizontal' - ", panel, isToolsBox);
        return isToolsBox ? { panel, isToolsBox } : { panel: node, isToolsBox: false };
      } else if (node.mode === 'vertical' && nLast) {
        return getBottomLeftPanelData(nLast);
      }
    }
    const isPanelData = 'tabs' in node && Array.isArray(node.tabs);
    // код для подсвечивания
    if (isPanelData && node.tabs.at(0)) {
      const tab = node.tabs.at(0);
      if (tab && tab.id) {
        setTimeout(() => {
          const div = document.getElementById(tab.id as string);
          div?.classList.add('tab-highlight');
        }, 1);
      }
    }
    const isToolsBox = isPanelData && checkOnToolsBoxValid(node);
    return { panel: node, isToolsBox };
  };

  const addToToolsBox = (id: ComponentType) => {
    if (dockRef.current) {
      if (isOpenedWindow(id)) {
        setWindowActive(id);
      } else {
        const tabData: TabData = loadTab({ id });
        const dockbox = dockRef.current.getLayout().dockbox;
        const { panel: parent, isToolsBox } = getBottomLeftPanelData(dockbox);
        console.log('getBottomLeftPanelData: ', parent, isToolsBox);
        //const { tab, panel, box, dockbox } = findPanels(LayoutGroupType.MODEL_TOOLSBOX, checkOnToolsBoxValid);
        //const parent = tab && tab.parent ? tab.parent : panel;
        if (parent && isToolsBox) {
          const { panel, direction } = getToolsBoxDirection(id, parent as PanelData);
          dockRef.current.dockMove(tabData, panel, direction);
        } else if (parent) {
          dockRef.current.dockMove(tabData, parent, 'bottom');
        } else if (dockbox) {
          dockRef.current.dockMove(tabData, dockbox, 'bottom');
        }
      }
    }
  };

  return { loadTab, isOpenedWindow, setWindowActive, addToWorkspace, addToToolsBox };
};

export default useDockLayout;
