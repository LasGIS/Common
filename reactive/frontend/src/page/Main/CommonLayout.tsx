import './styles.scss';
import React, { Suspense, useEffect, useState } from 'react';
import { LogoutOutlined } from '@ant-design/icons/lib/icons';
import { useSelector } from 'react-redux';
import { Dropdown, Layout, Menu, Spin } from 'antd';
import { findKeyByPathname, MENU_DATA, MenuData } from './MenuHelper';
import { Link, Outlet } from 'react-router-dom';
import { commonAuthUserSelector, commonLoadingSelector, commonSettingsSelector } from '../../reducer/common';
import { AppSettingsConfig, AuthUser } from '../../reducer/redux-types';
import { ItemType } from 'antd/lib/menu/hooks/useItems';
import HeaderLogo from '../../style/img/evolution.gif';
import UnknownUser from './UnknownUser.png';
import { useLocation } from 'react-router';

const { Content, Header, Sider, Footer } = Layout;

const MENU_WIDTH = 230;

const getItem = (menuData: MenuData): ItemType => {
  return {
    key: menuData.key,
    icon: menuData.icon,
    //    type: menuData.children ? "group" : undefined,
    label: menuData.link ? (
      <Link to={menuData.link} title={menuData.title}>
        {menuData.name}
      </Link>
    ) : (
      <span title={menuData.title}>{menuData.name}</span>
    ),
    children: menuData.children ? getItems(menuData.children) : undefined,
  };
};

const getItems = (menuDataList: MenuData[]): ItemType[] => {
  return menuDataList.map((menuData) => getItem(menuData));
};

const items: ItemType[] = getItems(MENU_DATA);

const headerMenu: ItemType[] = [
  {
    key: '1000',
    icon: <LogoutOutlined />,
    label: <a href="/logout">Выйти</a>,
  },
];

const CommonLayout = () => {
  const location = useLocation();
  const loading = useSelector(commonLoadingSelector) as boolean;
  const settings = useSelector(commonSettingsSelector) as AppSettingsConfig;
  const authUser = useSelector(commonAuthUserSelector) as AuthUser;
  const [isMenuCollapsed, setMenuCollapsed] = useState(false);
  const [selectedKeys, setSelectedKeys] = useState(findKeyByPathname(location.pathname));
  const menuWidth = isMenuCollapsed ? 40 : MENU_WIDTH;

  useEffect(() => {
    setSelectedKeys(findKeyByPathname(location.pathname));
  }, [location.pathname]);

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
