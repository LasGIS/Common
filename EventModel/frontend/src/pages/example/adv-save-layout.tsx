import React, { useRef, useState } from 'react';
import { htmlTab, tsxTab } from './prism-tabs';
import { DockLayout, TabBase, TabData, PanelBase, PanelData, LayoutData } from 'rc-dock';

const groups = {
  allowWindow: {
    floatable: true,
    newWindow: true,
    maximizable: true,
  },
};

export interface TabDataInputValue extends TabData {
  inputValue: string;
}

interface InputTabProps {
  tabData: TabDataInputValue;
}

class InputTab extends React.PureComponent<InputTabProps> {
  onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    this.props.tabData.inputValue = e.target.value;
    this.forceUpdate();
  };

  render() {
    return (
      <div>
        <p>input value will be saved</p>
        <input style={{ width: '100%' }} onChange={this.onChange} value={this.props.tabData.inputValue} />
      </div>
    );
  }

  static create(tabData: TabDataInputValue) {
    return <InputTab tabData={tabData} />;
  }
}

const tab0 = {
  id: 'tab0',
  title: 'tab0',
  content: <div>This tab will be added back to main panel every time you load layout.</div>,
};

const Demo: React.FC = () => {
  const dockLayoutRef = useRef<DockLayout>(null);

  const defaultLayout: LayoutData = {
    dockbox: {
      mode: 'horizontal',
      children: [
        {
          size: 200,
          tabs: [
            { id: 'tab1', closable: true },
            { id: 'tab2', closable: true },
          ] as TabData[],
        },
        {
          id: 'main-panel',
          size: 400,
          tabs: [{ id: 'tab0' }, { id: 'tsxTab' }, { id: 'htmlTab' }] as TabData[],
          panelLock: {
            panelStyle: 'main',
          },
        },
        {
          size: 200,
          tabs: [{ id: 'tab5' }, { id: 'tab6' }] as TabData[],
        },
      ],
    },
  };

  const [layout, setLayout] = useState<LayoutData>(defaultLayout);

  const saveTab = (tabData: TabData): TabBase => {
    const { id, inputValue } = tabData;
    // add inputValue from saved data;
    if (id === 'tab0') {
      return { id, inputValue: 0 };
    }
    return { id, inputValue };
  };

  const loadTab = (savedTab: TabBase): TabData => {
    const id = savedTab.id;

    switch (id) {
      case 'tab0':
        return tab0;
      case 'tsxTab':
        return tsxTab;
      case 'htmlTab':
        return htmlTab;
      default:
        return {
          id,
          title: id,
          content: InputTab.create,
          inputValue: savedTab.inputValue,
          group: 'allowWindow',
          closable: true,
        };
    }
  };

  // add tab0 to the main panel
  const afterPanelLoaded = (savedPanel: PanelBase, panelData: PanelData) => {
    const id = savedPanel.id;

    if (id === 'main-panel') {
      panelData.panelLock = {
        panelStyle: 'main',
      };
      panelData.tabs.unshift({ ...tab0 });
    }
  };

  return (
    <div>
      <DockLayout
        ref={dockLayoutRef}
        defaultLayout={defaultLayout}
        groups={groups}
        saveTab={saveTab}
        loadTab={loadTab}
        afterPanelLoaded={afterPanelLoaded}
        style={{ position: 'absolute', left: 10, top: 60, right: 10, bottom: 10 }}
      />
      <div className="top-panel">
        <button
          className="btn"
          style={{ marginRight: 20 }}
          onClick={() => {
            const l = dockLayoutRef.current?.saveLayout();
            setLayout(l as LayoutData);
          }}
        >
          Save Layout
        </button>
        <button
          className="btn"
          // disabled={this.state.saved == null}
          onClick={() => dockLayoutRef.current?.loadLayout(layout)}
        >
          Load Layout
        </button>
      </div>
    </div>
  );
};

export default Demo;
