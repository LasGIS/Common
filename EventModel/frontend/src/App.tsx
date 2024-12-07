import { useEffect } from 'react';
import store from '@/redux';
import { Provider } from 'react-redux';
import { RouterProvider } from 'react-router-dom';
import './App.css';
import routerConfig from '@/router/Router.tsx';

function App() {
  useEffect(() => {
    document.title = `EventModel WebSocket v${import.meta.env.PACKAGE_VERSION}`;
  }, []);

  return (
    <Provider store={store}>
      <RouterProvider router={routerConfig} />
    </Provider>
  );
}

export default App;
