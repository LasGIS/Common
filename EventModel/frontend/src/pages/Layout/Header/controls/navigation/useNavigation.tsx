import { useState } from 'react';
import { MenuProps } from 'antd/es/menu/menu';
import { Translation } from 'react-i18next';

interface Props {
  showAboutModal: () => void;
}

const useNavigation = ({ showAboutModal }: Props) => {
  const items = [
    {
      key: 'create',
      label: <Translation>{(t) => t('common.actions.create')}</Translation>,
      onClick: () => {
        console.log('common.actions.create');
      },
      disabled: false,
    },
    {
      key: 'open',
      label: <Translation>{(t) => t('common.actions.open')}</Translation>,
      onClick: () => {
        console.log('common.actions.create');
      },
      disabled: false,
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
