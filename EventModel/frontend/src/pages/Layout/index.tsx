import React from 'react';
import Header from '@pages/Layout/Header';
import Footer from '@pages/Layout/Footer';
import { createGlobalStyle } from 'styled-components';
import { Outlet } from 'react-router-dom';

const GlobalStyle = createGlobalStyle`
`;

const BaseLayout: React.FC = () => {
  return (
    <>
      <GlobalStyle />
      <Header />
      <Outlet />
      <Footer />
    </>
  );
};

export default BaseLayout;
