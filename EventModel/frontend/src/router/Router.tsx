import { createBrowserRouter } from 'react-router-dom';
import WindowPage from '@pages/WindowPage';
import DemoPage from '@pages/DemoPage';
import BaseLayout from '@pages/Layout';

const routerConfig = createBrowserRouter([
  {
    path: '/',
    element: <BaseLayout />,
    children: [
      {
        index: true,
        element: <WindowPage />,
      },
      {
        path: '/demo',
        element: <DemoPage />,
      },
    ],
  },
]);

export default routerConfig;
