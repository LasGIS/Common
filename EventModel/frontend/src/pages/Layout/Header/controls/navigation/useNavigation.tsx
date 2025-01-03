import { useState } from 'react';
import { MenuProps } from 'antd/es/menu/menu';
import { Translation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';

interface Props {
  showAboutModal: () => void;
}

const useNavigation = ({ showAboutModal }: Props) => {
  // const [t] = useTranslation();
  const navigate = useNavigate();
  const items = [
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
      key: 'about',
      label: <Translation>{(t) => t('header.menu.about')}</Translation>,
      onClick: showAboutModal,
    },
  ];

  const [dropdownItems] = useState<MenuProps['items']>(items);

  return dropdownItems;
};

export default useNavigation;
