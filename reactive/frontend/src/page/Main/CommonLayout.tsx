import './styles.scss';
import React, { Suspense, useState } from 'react';
import { AppstoreOutlined, DesktopOutlined, LoginOutlined, LogoutOutlined, MailOutlined, SettingOutlined } from '@ant-design/icons/lib/icons';
import { useSelector } from 'react-redux';
import type { MenuProps } from 'antd';
import { Dropdown, Layout, Menu, Spin } from 'antd';
import { findKeyByPathname } from './MenuHelper';
import { Link, Outlet } from 'react-router-dom';
import { commonAuthUserSelector, commonLoadingSelector, commonSettingsSelector } from '../../reducer/common';
import { AppSettingsConfig, AuthUser } from '../../reducer/redux-types';
import { ItemType } from 'antd/lib/menu/hooks/useItems';
import HeaderLogo from '../../style/img/evolution.gif';
import UnknownUser from './UnknownUser.png';

const { Content, Header, Sider, Footer } = Layout;

const MENU_WIDTH = 230;

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
  const loading = useSelector(commonLoadingSelector) as boolean;
  const settings = useSelector(commonSettingsSelector) as AppSettingsConfig;
  const authUser = useSelector(commonAuthUserSelector) as AuthUser;
  const [isMenuCollapsed, setMenuCollapsed] = useState(false);
  const menuWidth = isMenuCollapsed ? 40 : MENU_WIDTH;
  const selectedKeys = [findKeyByPathname(window.location.pathname)];

  const headerMenu: ItemType[] = [
    {
      key: '1000',
      icon: <LogoutOutlined />,
      label: <a href="/logout">Выйти</a>,
    },
  ];

  return (
    <Spin spinning={loading} size="large" tip="Загрузка данных">
      <Layout style={{ minHeight: '100vh', minWidth: '100vw' }}>
        <Header>
          <div className="header-logo">
            <img src={HeaderLogo} alt="Логотип" style={{ height: 40, width: 50 }} />
          </div>
          <div className="header-text">
            <h3>Редактирование генеалогических связей</h3>
          </div>
          <div className="header-auth">
            <p>
              {authUser?.firstName} {authUser?.lastName}
            </p>
            <Dropdown className="header-auth" menu={{ items: headerMenu }} placement="topLeft">
              <img src={UnknownUser} alt="изображение пользователя" />
            </Dropdown>
          </div>
        </Header>
        <Layout className="site-layout">
          <Sider collapsible collapsed={isMenuCollapsed} onCollapse={(collapsed) => setMenuCollapsed(collapsed)} width={menuWidth} theme="light">
            <Menu mode="inline" selectedKeys={selectedKeys} items={items} />
          </Sider>
          <Content
            style={{
              margin: '24px 24px 0 24px',
              borderRadius: '16px',
              padding: '24px 32px',
              background: '#FFFFFF',
              minHeight: 280,
            }}
          >
            <Suspense fallback={<div>Загрузка...</div>}>
              <Outlet />
            </Suspense>
          </Content>
        </Layout>
        <Footer style={{ textAlign: 'left', background: '#F5F5F5', color: '#8C8C8C', fontSize: 14 }}>
          {`${settings?.name}`} &nbsp; &nbsp; {`version: ${settings?.version}`}
        </Footer>
      </Layout>
    </Spin>
  );
};

export default CommonLayout;
