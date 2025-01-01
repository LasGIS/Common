import { Dropdown, Modal } from 'antd';
import { Translation } from 'react-i18next';
import useNavigation from './useNavigation.tsx';
import { About } from './About.tsx';
import { Logo } from './Logo.tsx';

const Navigation = () => {
  const [modal, contextHolder] = Modal.useModal();

  const items = useNavigation({ showAboutModal });

  function showAboutModal() {
    modal.info({
      width: '500px',
      centered: true,
      title: <Translation>{(t) => t('aboutModal.title')}</Translation>,
      content: <About />,
    });
  }

  return (
    <>
      <Dropdown menu={{ items }}>
        <div>
          <Logo />
        </div>
      </Dropdown>
      {contextHolder}
    </>
  );
};

export default Navigation;
