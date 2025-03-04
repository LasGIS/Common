import React, { useState } from 'react';
import { MenuProps } from 'antd/es/menu/menu';
import { Translation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import type { ItemType } from 'antd/es/menu/interface';

interface Props {
  showAboutModal: () => void;
}

const useNavigation = ({ showAboutModal }: Props) => {
  const navigate = useNavigate();
  const items: ItemType[] = [
    {
      key: 'main',
      label: <Translation>{(t) => t('header.menu.main')}</Translation>,
      onClick: () => navigate('/'),
      disabled: false,
    },
    {
      key: 'demo',
      label: <Translation>{(t) => t('header.menu.demo')}</Translation>,
      onClick: () => navigate('/demo'),
      disabled: false,
    },
    {
      key: 'external',
      label: <Translation>{(t) => t('header.menu.external')}</Translation>,
      children: [
        {
          key: 'ext-8088',
          label: (
            <a href="http://localhost:8088/demo/index.html?" target="_blank">
              :8088/demo
            </a>
          ),
        },
        {
          key: 'ext-8090',
          label: (
            <a href="http://localhost:8090/demo/index.html?" target="_blank">
              :8090/demo
            </a>
          ),
        },
      ],
    },
    {
      key: 'about',
      label: <Translation>{(t) => t('header.menu.about')}</Translation>,
      onClick: showAboutModal,
    },
  ];

  const [dropdownItems] = useState<MenuProps['items']>(items);

  return dropdownItems;
};

export default useNavigation;
