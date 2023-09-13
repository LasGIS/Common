import React from 'react';
import { LoginOutlined, SettingOutlined, UserOutlined, UserSwitchOutlined } from '@ant-design/icons';

export type MenuData = {
  key: string;
  name: string;
  title?: string;
  link?: string;
  params?: string;
  optionKey?: string;
  icon?: React.ReactNode;
  children?: MenuData[];
};

export const MENU_DATA: MenuData[] = [
  {
    key: 'person',
    name: 'Персоны',
    title: 'Персоны, участвующие в строительстве генеалогического дерева',
    link: '/person',
    icon: <UserSwitchOutlined />,
  },
  {
    key: 'admin',
    name: 'Администрирование',
    icon: <SettingOutlined />,
    children: [
      {
        key: 'user',
        name: 'Пользователи',
        title: 'Пользователи, имеющие доступ в систему',
        link: '/user',
        icon: <UserOutlined />,
      },
      {
        key: 'login',
        name: 'Login',
        link: '/login',
        icon: <LoginOutlined />,
      },
    ],
  },
];

export const findKeyByPathname = (pathname: string): string[] => {
  const accKeys: string[] = [];

  const findMenuData = (menus: MenuData[], keys: string[]): MenuData | undefined => {
    let menu: MenuData | undefined = undefined;
    menus.forEach((menuData) => {
      if (menuData.children) {
        menu = findMenuData(menuData.children, keys);
        if (menu) {
          keys.splice(0, 0, menuData.key);
        }
      } else if (menuData.link && pathname.startsWith(menuData.link)) {
        menu = menuData;
        keys.push(menu.key);
      }
    });
    return menu;
  };

  findMenuData(MENU_DATA, accKeys);
  return accKeys;
};
