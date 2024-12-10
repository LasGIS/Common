import { createBrowserRouter } from 'react-router-dom';
import WindowPage from '@pages/WindowPage';
import DemoPage from '@pages/DemoPage';


const routerConfig = createBrowserRouter([
  { path: '/', element: (<WindowPage />) },
  { path: '/dmo', element: (<DemoPage />) },
]);

export default routerConfig;
