import React, { ComponentType } from "react";
import { DesktopOutlined } from "@ant-design/icons/lib/icons";
import { ConnectedComponent } from "react-redux";
import CounterPage from "../Counter/CounterPage";

export type MenuData = {
  key: string;
  name: string;
  pathname: string;
  params?: string;
  optionKey?: string;
  icon: React.ReactNode;
  component?: ConnectedComponent<ComponentType<any>, any>;
  element?: any;
};

export const MENU_DATA: { [key: string]: MenuData } = {
  Counter: {
    key: '1',
    name: 'CounterPage',
    pathname: '/counter',
    icon: <DesktopOutlined />,
    element: CounterPage,
  },
};

export const MENU_DATA_LIST: MenuData[] = [MENU_DATA.Counter];

export const findKeyByPathname = (pathname: string): string => {
  const menu = MENU_DATA_LIST.find((menuData) => pathname.startsWith(menuData.pathname));
  return menu ? menu.key : '';
};
