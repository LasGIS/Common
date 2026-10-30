import React, { useState, useRef } from 'react';
import { RcDockContent } from './style';
import { BoxBase, DockLayout, LayoutBase, PanelBase, TabBase, TabData } from 'rc-dock';
import 'rc-dock/dist/rc-dock.css';
import { removeCyclicReferences } from './utils';
import { tabsRegistry } from './components/TabsRegistry';
import { modelingLayoutData } from './context/dockConst';

// Ключ для хранения данных в LocalStorage
const LOCAL_STORAGE_KEY = 'my-dock-layout';

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

  // 2. Функция сохранения в LocalStorage при любых изменениях
  const handleLayoutChange = (newLayout: LayoutBase) => {
    setLayout(newLayout);

    // Перед сохранением очищаем свойства content, так как JSX не сериализуется в JSON
    const cleanLayout = removeCyclicReferences(newLayout);

    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(cleanLayout));
  };

  // 3. Динамическое добавление новой вкладки
  const addNewTab = () => {
    const newTabId = `dynamic-${Date.now()}`;
    const newTab = {
      id: newTabId,
      title: `Вкладка ${newTabId.slice(-4)}`,
      content: <div style={{ padding: 15 }}>Это созданная кнопкой вкладка {newTabId}</div>,
      closable: true, // Разрешаем закрывать эту вкладку
    };

    // Используем API ref от rc-dock для точечного добавления вкладки во вселенную доков
    // Добавляем во вкладку 'left-panel' в качестве примера
    if (dockRef.current) {
      dockRef.current.dockMove(newTab, 'left-panel', 'middle');
    }
  };

  return (
    <RcDockContent>
      <div style={{ width: '100%', height: '100%', display: 'flex', flexDirection: 'row' }}>
        {/* Панель управления с кнопкой */}
        <div style={{ padding: 10, background: '#f5f5f5', borderBottom: '1px solid #ddd' }}>
          <button onClick={addNewTab} style={{ padding: '8px 16px', cursor: 'pointer' }}>
            ➕
          </button>
        </div>

        {/* Контейнер для DockLayout */}
        <div style={{ flexGrow: 1, position: 'relative' }}>
          <DockLayout
            ref={dockRef}
            layout={layout}
            onLayoutChange={handleLayoutChange}
            style={{ position: 'absolute', left: 4, top: 4, right: 4, bottom: 4 }}
            saveTab={(tabData) => tabData}
            loadTab={loadTab}
          />
        </div>
      </div>
    </RcDockContent>
  );
};

export default RcDockPage;
