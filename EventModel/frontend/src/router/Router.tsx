import { createBrowserRouter } from 'react-router-dom';
import MainPage from '@pages/MainPage';
import AdvancedSaveLayout from '@/pages/example/AdvancedSaveLayout';
import DemoPage from '@pages/DemoPage';
import BaseLayout from '@pages/BaseLayout';
import NoMatchPage from '@pages/NoMatchPage';
import RcDockPage from '@pages/RcDockPage';
import ProjectResults from '@pages/ProjectResults';

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
        path: '/adv-save-layout',
        element: <AdvancedSaveLayout />,
      },
      {
        path: '/project-results',
        element: <ProjectResults />,
      },
      {
        path: '*',
        element: <NoMatchPage />,
      },
    ],
  },
]);

export default routerConfig;
