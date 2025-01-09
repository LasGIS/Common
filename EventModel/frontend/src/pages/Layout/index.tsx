import React from 'react';
import Header from '@pages/Layout/Header';
import Footer from '@pages/Layout/Footer';
import { createGlobalStyle } from 'styled-components';
import { RouteType, useRoute } from '@/router/RouteProvider.tsx';
import MainPage from '@pages/MainPage';
import DemoPage from '@pages/DemoPage';
import NoMatchPage from '@pages/NoMatchPage';

const GlobalStyle = createGlobalStyle`
`;

const BaseLayout: React.FC = () => {
  const { route } = useRoute();

  const toRoute = () => {
    switch (route) {
      case RouteType.MAIN_PAGE:
        return <MainPage />;
      case RouteType.DEMO_PAGE:
        return <DemoPage />;
      case RouteType.NO_MATCH_PAGE:
        return <NoMatchPage />;
    }
  };

  return (
    <>
      <GlobalStyle />
      <Header />
      {toRoute()}
      <Footer />
    </>
  );
};

export default BaseLayout;
