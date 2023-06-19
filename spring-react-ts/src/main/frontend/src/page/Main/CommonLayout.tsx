import { DesktopOutlined, LogoutOutlined } from '@ant-design/icons/lib/icons';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { Divider, Layout, Menu, Spin, theme } from 'antd';
import { findKeyByPathname } from './MenuHelper';
import { Link, Outlet } from 'react-router-dom';
import { commonLoadingSelector } from '../../reducer/common';

const { Content, Header, Sider } = Layout;

const CommonLayout = () => {
  // const dispatch = useDispatch();
  const loading = useSelector(commonLoadingSelector) as boolean;
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
        </Sider>
        <Layout className="site-layout" style={{ marginLeft: menuWidth }}>
          <Header style={{ padding: 0, background: token.colorBgLayout }} />
          <Content
            style={{
              margin: '24px 16px',
              padding: 24,
              background: '#fff',
              minHeight: 280,
            }}
          >
            <Outlet />
          </Content>
        </Layout>
      </Layout>
    </Spin>
  );
};

export default CommonLayout;
