import React, { PropsWithChildren } from 'react';
import Header from '@pages/Layout/Header';
import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
`;

const BaseLayout: React.FC<PropsWithChildren> = ({ children }) => {
  return (
    <>
      <GlobalStyle />
      <Header />
      {children}
    </>
  );
};

export default BaseLayout;
