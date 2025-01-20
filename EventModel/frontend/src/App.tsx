import { useEffect } from 'react';
import store from '@/redux';
import { Provider } from 'react-redux';
import { RouterProvider } from 'react-router-dom';
import './App.scss';
import routerConfig from '@/router/Router.tsx';
import Providers from '@components/Providers.tsx';

function App() {
  useEffect(() => {
    document.title = `EventModel WebSocket v${import.meta.env.PACKAGE_VERSION}`;
  }, []);

  return (
    <Provider store={store}>
      <Providers>
        <RouterProvider router={routerConfig} />
      </Providers>
    </Provider>
  );
}

export default App;
