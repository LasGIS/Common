import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Divider, Layout, Menu, Spin } from "antd";
import { findKeyByPathname, MENU_DATA_LIST, MenuData } from "./MenuHelper";
import { Link, Outlet } from "react-router-dom";
import { LogoutOutlined } from "@ant-design/icons/lib/icons";
import { commonLoadingSelector } from "../../redux/common";

const { Content, Sider } = Layout;

const CommonLayout = () => {
  const dispatch = useDispatch();
  const loading = useSelector(commonLoadingSelector);
  const [isMenuCollapsed, setMenuCollapsed] = useState(false);
  const menuWidth = isMenuCollapsed ? 80 : 200;
  const selectedKeys = [findKeyByPathname(window.location.pathname)];

  return (
    <Spin spinning={loading} size="large" tip="Загрузка данных">
      <Layout style={{ minHeight: '100vh' }}>
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
            {MENU_DATA_LIST.map((menu: MenuData) => (
              <Menu.Item key={menu.key}>
                {menu.icon}
                <span>{menu.name}</span>
                <Link to={menu.pathname} />
              </Menu.Item>
            ))}
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
