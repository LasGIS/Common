import 'rc-dock/dist/rc-dock.css';
import React, { useState, useRef, useEffect } from 'react';
import { ControlPanel, RcDockButton, RcDockButtonGroup, RcDockContent, RcDockLayout } from './style';
import { BoxBase, DockLayout, LayoutBase, PanelBase, TabData } from 'rc-dock';
import { ReactComponent as TableIcon } from '@/assets/rc-dock/Table.svg';
import { ReactComponent as ObjectsIcon } from '@/assets/rc-dock/Objects.svg';
import { ReactComponent as SchemeIcon } from '@/assets/rc-dock/Scheme.svg';
import { ReactComponent as StatisticsIcon } from '@/assets/rc-dock/Statistics.svg';
import { ReactComponent as CodeSearchIcon } from '@/assets/rc-dock/CodeSearch.svg';
import { ReactComponent as ReportIcon } from '@/assets/rc-dock/Report.svg';
import { ReactComponent as AnalysisIcon } from '@/assets/rc-dock/Analysis.svg';
import { ComponentType } from './types';
import { removeCyclicReferences } from './utils';
import { tabsRegistry } from './components/TabsRegistry';
import { modelingLayoutData } from './context/dockConst';
import useDockLayout from './hooks/useDockLayout';

// Ключ для хранения данных в LocalStorage
const LOCAL_STORAGE_KEY = 'my-dock-layout';

const BOTTON_COMPONENT_TYPES = [ComponentType.MODEL_MODELING_TABLES,
ComponentType.MODEL_MODELING_OBJECTS,
ComponentType.MODEL_MODELING_SCHEME,
ComponentType.MODEL_STATISTICS,
ComponentType.MODEL_CODE_SEARCH,
ComponentType.MODEL_RUN_LOG,]

// Функция для безопасной загрузки раскладки
const loadLayout = (): LayoutBase => {
  const saved = localStorage.getItem(LOCAL_STORAGE_KEY);
  if (saved) {
    try {
      // В реальном проекте content (JSX) нельзя сохранить в JSON.
      // Поэтому мы сохраняем только структуру, а контент восстанавливаем (см. ниже)
      return JSON.parse(saved) as LayoutBase;
    } catch (e) {
      console.error('Ошибка парсинга layout из LocalStorage', e);
    }
  }
  return modelingLayoutData;
};

const RcDockPage: React.FC = () => {
  const dockRef = useRef<DockLayout | null>(null);
  const { loadTab, addToToolsBox, addToWorkspace, isOpenedWindow } = useDockLayout(dockRef);
  const [openedWindow, setOpenedWindow] = useState<Record<string, boolean>>({
    [ComponentType.MODEL_MODELING_TABLES]: false,
    [ComponentType.MODEL_MODELING_OBJECTS]: false,
    [ComponentType.MODEL_MODELING_SCHEME]: true,
    [ComponentType.MODEL_STATISTICS]: false,
    [ComponentType.MODEL_CODE_SEARCH]: false,
    [ComponentType.MODEL_RUN_LOG]: false,
  });

  // Инициализируем состояние из LocalStorage
  const [layout, setLayout] = useState<LayoutBase>(() => {
    const savedLayout = loadLayout();

    // Рекурсивная функция для восстановления JSX-контента в загруженной структуре
    const restoreContent = (box: BoxBase | PanelBase) => {
      if ((box as PanelBase).tabs) {
        (box as PanelBase).tabs.forEach((tab) => {
          const data = tab.id && tabsRegistry.initializeTab(tab, {});
          if (data) {
            const tabData = tab as TabData;
            tabData.title = data.title;
            tabData.content = data.content;
          } else {
            tab.content = <div style={{ padding: 15 }}>Динамический контент {tab.id}</div>;
          }
        });
      }
      if ((box as BoxBase).children) {
        (box as BoxBase).children.forEach(restoreContent);
      }
    };

    if (savedLayout.dockbox) restoreContent(savedLayout.dockbox);
    if (savedLayout.floatbox) restoreContent(savedLayout.floatbox);

    return savedLayout;
  });

  const refreshOpenedWindow = () =>
    setTimeout(() => BOTTON_COMPONENT_TYPES.forEach(key => setOpenedWindow((prev) => ({ ...prev, [key]: isOpenedWindow(key) }))), 1);

  useEffect(() => { refreshOpenedWindow(); }, []);

  // 2. Функция сохранения в LocalStorage при любых изменениях
  const handleLayoutChange = (newLayout: LayoutBase) => {
    setLayout(newLayout);

    // Перед сохранением очищаем свойства content, так как JSX не сериализуется в JSON
    const cleanLayout = removeCyclicReferences(newLayout);

    refreshOpenedWindow();

    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(cleanLayout));
  };

  const restoreLocalStorage = () => {
    setLayout(modelingLayoutData);
    refreshOpenedWindow();
  };

  const openWorkspace = (type: ComponentType) => {
    addToWorkspace(type);
    // refreshOpenedWindow();
  }

  const openToolsBox = (type: ComponentType) => {
    addToToolsBox(type);
    // refreshOpenedWindow();
  }

  return (
    <RcDockContent>
      <div style={{ width: '100%', height: '100%', display: 'flex', flexDirection: 'row' }}>
        {/* Панель управления с кнопкой */}
        <ControlPanel>
          <RcDockButtonGroup>
            <RcDockButton style={{ background: openedWindow[ComponentType.MODEL_MODELING_TABLES] ? '#ff8888' : '' }}
              type="text"
              onClick={() => openWorkspace(ComponentType.MODEL_MODELING_TABLES)}
              icon={<TableIcon />}
            />
            <RcDockButton style={{ background: openedWindow[ComponentType.MODEL_MODELING_OBJECTS] ? '#ff8888' : '' }}
              type="text"
              onClick={() => openWorkspace(ComponentType.MODEL_MODELING_OBJECTS)}
              icon={<ObjectsIcon />}
            />
            <RcDockButton style={{ background: openedWindow[ComponentType.MODEL_MODELING_SCHEME] ? '#ff8888' : '' }}
              type="text"
              onClick={() => openWorkspace(ComponentType.MODEL_MODELING_SCHEME)}
              icon={<SchemeIcon />}
            />
          </RcDockButtonGroup>
          <RcDockButtonGroup>
            <RcDockButton type="text" onClick={restoreLocalStorage} icon={<AnalysisIcon />} />
          </RcDockButtonGroup>
          <RcDockButtonGroup>
            <RcDockButton style={{ background: openedWindow[ComponentType.MODEL_STATISTICS] ? '#ff8888' : '' }}
              type="text"
              onClick={() => openToolsBox(ComponentType.MODEL_STATISTICS)}
              icon={<StatisticsIcon />}
            />
            <RcDockButton style={{ background: openedWindow[ComponentType.MODEL_CODE_SEARCH] ? '#ff8888' : '' }}
              type="text"
              onClick={() => openToolsBox(ComponentType.MODEL_CODE_SEARCH)}
              icon={<CodeSearchIcon />}
            />
            <RcDockButton style={{ background: openedWindow[ComponentType.MODEL_RUN_LOG] ? '#ff8888' : '' }}
              type="text"
              onClick={() => openToolsBox(ComponentType.MODEL_RUN_LOG)}
              icon={<ReportIcon />}
            />
          </RcDockButtonGroup>
        </ControlPanel>

        {/* Контейнер для DockLayout */}
        <RcDockLayout style={{ flexGrow: 1, position: 'relative' }}>
          <DockLayout
            ref={dockRef}
            layout={layout}
            onLayoutChange={handleLayoutChange}
            style={{ position: 'absolute', left: 4, top: 4, right: 4, bottom: 4 }}
            saveTab={(tabData) => tabData}
            loadTab={loadTab}
          />
        </RcDockLayout>
      </div>
    </RcDockContent>
  );
};

export default RcDockPage;
