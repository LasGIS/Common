import type { PropsWithChildren } from 'react';
import React from 'react';
import { I18nextProvider } from 'react-i18next';
import i18nInstance, { useLanguageDetector } from '@/i18n';
import { AliasToken } from 'antd/es/theme/interface';
import { colors, darkThemeColorsByElement, liteThemeColorsByElement } from '@components/const.ts';
import { ThemeProvider } from 'styled-components';
import { AlgorithmType } from '@/types/redux/SettingsTypes';
import { ThemeConfig } from 'antd';
import { useSelector } from 'react-redux';
import { algorithmSelector } from '@/redux/reducer/SettingsReducer';

type CustomThemeColors = typeof liteThemeColorsByElement & typeof darkThemeColorsByElement;

export type AppToken = CustomThemeColors &
  Pick<
    AliasToken,
    'colorPrimary' | 'colorPrimaryHover' | 'colorText' | 'boxShadow' | 'fontSize' | 'fontFamily' | 'borderRadius'
  > & { colors: Record<string, string> };

const regularTheme: AppThemeConfig = {
  token: {
    fontSize: 12,
    fontFamily: 'Roboto, Arial, Inter, "Segoe UI", system-ui,  BlinkMacSystemFont, Avenir, Helvetica, sans-serif',
    borderRadius: 2,
    colorPrimary: colors.blue,
    colorPrimaryHover: colors.antBlue80,
    colorText: colors.black,
    boxShadow: '0 2px 8px rgba(0,0,0,0.15)',
    ...liteThemeColorsByElement,
    colors,
  },
  components: {},
};

const darkTheme: AppThemeConfig = {
  token: {
    fontSize: 12,
    fontFamily: 'Roboto, Arial, Inter, "Segoe UI", system-ui,  BlinkMacSystemFont, Avenir, Helvetica, sans-serif',
    borderRadius: 2,
    colorPrimary: colors.blue,
    colorPrimaryHover: colors.antBlue80,
    colorText: colors.black,
    boxShadow: '0 2px 8px rgba(0,0,0,0.15)',
    ...darkThemeColorsByElement,
    colors,
  },
  components: {},
};

type AntComponents = NonNullable<ThemeConfig['components']>;

export interface AppThemeConfig extends ThemeConfig {
  token: AppToken;
  components: AntComponents;
}

/*interface UseAppTokenReturn extends Omit<ReturnType<typeof useToken>, 'token'> {
  token: AppToken;
}*/

export const themes: Record<AlgorithmType, AppThemeConfig> = {
  [AlgorithmType.DEFAULT]: regularTheme,
  [AlgorithmType.DARK]: darkTheme,
};

const Providers: React.FC<PropsWithChildren> = ({ children }) => {
  const algorithm = useSelector(algorithmSelector);
  useLanguageDetector(i18nInstance);

  return (
    <I18nextProvider i18n={i18nInstance}>
      <ThemeProvider theme={themes[algorithm as AlgorithmType]}>{children}</ThemeProvider>
    </I18nextProvider>
  );
};

export default Providers;
