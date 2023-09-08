import React, { useState } from 'react';
import { AppstoreOutlined, DesktopOutlined, LoginOutlined, MailOutlined, SettingOutlined } from '@ant-design/icons/lib/icons';
import { useSelector } from 'react-redux';
import type { MenuProps } from 'antd';
import { Layout, Menu, Spin, theme } from 'antd';
import { findKeyByPathname } from './MenuHelper';
import { Link, Outlet } from 'react-router-dom';
import { commonLoadingSelector, commonSettingsSelector } from '../../reducer/common';
import { AppSettingsConfig } from '../../reducer/redux-types';
import { ItemType } from 'antd/lib/menu/hooks/useItems';
import { AppDispatch, useAppDispatch } from '../../reducer/store';

const { Content, Header, Sider, Footer } = Layout;

type MenuItem = Required<MenuProps>['items'][number];

const getItem = (label: React.ReactNode, key: React.Key, icon?: React.ReactNode, children?: MenuItem[], type?: 'group'): MenuItem => {
  return { key, icon, children, label, type } as MenuItem;
};

const items: ItemType[] = [
  { key: 1, icon: <DesktopOutlined />, label: <Link to="/user">Пользователи</Link> },
  { key: 2, icon: <LoginOutlined />, label: <Link to="/login">Login</Link> },
  // <Divider type="horizontal" style={{ margin: '6px 0' }} />,
  getItem('Navigation One', 'sub1', <MailOutlined />, [
    getItem('Item 2', 'g2', null, [getItem('Option 3', '33'), getItem('Option 4', '4')], 'group'),
  ]),
  getItem('Navigation Two', 'sub2', <AppstoreOutlined />, [
    getItem('Option 5', '5'),
    getItem('Option 6', '6'),
    getItem('Submenu', 'sub3', null, [getItem('Option 7', '7'), getItem('Option 8', '8')]),
  ]),
  { type: 'divider', theme: 'light', style: { margin: '6px 0' } } as MenuItem,
  getItem('Navigation Three', 'sub4', <SettingOutlined />, [getItem('Option 9 group', '9'), getItem('Option 10 group', '10')], 'group'),
  getItem('Option 11', '11'),
  getItem('Option 12', '12'),
  getItem('Group', 'grp', null, [getItem('Option 13', '13'), getItem('Option 14', '14')], 'group'),
];

const CommonLayout = () => {
  const dispatch: AppDispatch = useAppDispatch();
  const loading = useSelector(commonLoadingSelector) as boolean;
  const settings = useSelector(commonSettingsSelector) as AppSettingsConfig;
  const [isMenuCollapsed, setMenuCollapsed] = useState(false);
  const menuWidth = isMenuCollapsed ? 80 : 200;
  const selectedKeys = [findKeyByPathname(window.location.pathname)];

  const { token } = theme.useToken();
  return (
    <Spin spinning={loading} size="large" tip="Загрузка данных">
      <Layout style={{ minHeight: '100vh', minWidth: '100vw' }}>
        <Sider
          collapsible
          collapsed={isMenuCollapsed}
          onCollapse={(collapsed) => setMenuCollapsed(collapsed)}
          style={{
            overflow: 'auto',
            height: '100vh',
            position: 'fixed',
            left: 0,
          }}
        >
          <Menu theme="dark" mode="inline" selectedKeys={selectedKeys} items={items} />
          {/*
          <Menu theme="dark" mode="inline" selectedKeys={selectedKeys}>
            <Menu.Item key={1}>
              <DesktopOutlined />
              <span>Counter Page</span>
              <Link to="/counter" />
            </Menu.Item>
            <Menu.Item key="998" disabled style={{ height: '12px', cursor: 'auto' }}>
              <Divider type="horizontal" style={{ margin: '6px 0' }} />
            </Menu.Item>
            <Menu.Item key="999">
              <LogoutOutlined />
              <span>
                <a href="/logout">Logout</a>
              </span>
            </Menu.Item>
          </Menu>
*/}
        </Sider>
        <Layout className="site-layout" style={{ marginLeft: menuWidth }}>
          <Header style={{ padding: 0, background: token.colorBgLayout }} />
          <Content
            style={{
              margin: '24px 16px 0',
              padding: 24,
              background: '#fff',
              minHeight: 280,
            }}
          >
            <Outlet />
          </Content>
          <Footer style={{ textAlign: 'right' }}>
            {`${settings?.name}`} &nbsp; &nbsp; {`version: ${settings?.version}`}
          </Footer>
        </Layout>
      </Layout>
    </Spin>
  );
};

export default CommonLayout;
