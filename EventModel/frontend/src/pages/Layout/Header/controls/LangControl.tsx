import { Dropdown, MenuProps, Space } from 'antd';
import { DownOutlined } from '@ant-design/icons';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { SUPPORTED_LANGUAGES } from '@/types/redux/SettingsTypes.ts';
import { Translation } from 'react-i18next';
import { langSelector } from '@/redux/reducer/SettingsReducer.ts';
import { useLanguageChanger } from './hooks/useLanguageChanger.ts';

const StyledSpace = styled(Space)`
  cursor: pointer;
  margin-left: 20px;
  color: #1c4682;

  [role='img'] {
    font-size: 12px;
  }
`;

const LangControl = () => {
  const lang = useSelector(langSelector);

  const changeLanguage = useLanguageChanger();

  const items: MenuProps['items'] = SUPPORTED_LANGUAGES.map((lang) => ({
    key: lang,
    label: <Translation>{(t) => t(`header.lang.${lang}`)}</Translation>,
    onClick: () => changeLanguage(lang),
  }));

  return (
    <Dropdown menu={{ items }}>
      <StyledSpace>
        <Translation>{(t) => t(`header.lang.${lang}`)}</Translation>
        <DownOutlined style={{ fill: '#1C4682' }} />
      </StyledSpace>
    </Dropdown>
  );
};

export default LangControl;
