import { createBrowserRouter } from 'react-router-dom';
import MainPage from '@pages/MainPage';
import DemoPage from '@pages/DemoPage';
import BaseLayout from '@pages/Layout';
import NoMatchPage from '@pages/NoMatchPage';

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
        path: '*',
        element: <NoMatchPage />,
      },
    ],
  },
]);

export default routerConfig;
