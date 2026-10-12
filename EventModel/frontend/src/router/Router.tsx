import { createBrowserRouter } from 'react-router-dom';
import MainPage from '@pages/MainPage';
import DemoPage from '@pages/DemoPage';
import BaseLayout from '@pages/BaseLayout';
import NoMatchPage from '@pages/NoMatchPage';
import RcDockPage from '@pages/RcDockPage';

const routerConfig = createBrowserRouter([
  {
    path: '/',
    element: <BaseLayout />,
    children: [
      {
        index: true,
        element: <MainPage />,
      },
      {
        path: '/demo',
        element: <DemoPage />,
      },
      {
        path: '/rc-dock',
        element: <RcDockPage />,
      },
      {
        path: '*',
        element: <NoMatchPage />,
      },
    ],
  },
]);

export default routerConfig;
