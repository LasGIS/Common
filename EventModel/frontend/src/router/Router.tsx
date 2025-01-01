import { createBrowserRouter } from 'react-router-dom';
import WindowPage from '@pages/WindowPage';
import DemoPage from '@pages/DemoPage';
import BaseLayout from '@pages/Layout';

const routerConfig = createBrowserRouter([
  {
    path: '/',
    element: (
      <BaseLayout>
        <WindowPage />
      </BaseLayout>
    ),
  },
  {
    path: '/dmo',
    element: (
      <BaseLayout>
        <DemoPage />
      </BaseLayout>
    ),
  },
]);

export default routerConfig;
