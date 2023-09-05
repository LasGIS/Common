import React from 'react';
import {DesktopOutlined} from '@ant-design/icons/lib/icons';

export type MenuData = {
  key: string;
  name: string;
  pathname: string;
  params?: string;
  optionKey?: string;
  icon: React.ReactNode;
};

export const MENU_DATA: { [key: string]: MenuData } = {
  Counter: {
    key: '1',
    name: 'CounterPage',
    pathname: '/counter',
    icon: <DesktopOutlined />,
  },
  login: {
    key: '2',
    name: 'CounterPage',
    pathname: '/counter',
    icon: <DesktopOutlined />,
  },
};

export const MENU_DATA_LIST: MenuData[] = [MENU_DATA.Counter];

export const findKeyByPathname = (pathname: string): string => {
  const menu = MENU_DATA_LIST.find((menuData) => pathname.startsWith(menuData.pathname));
  return menu ? menu.key : '';
};
