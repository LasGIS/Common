import { useEffect } from 'react';
import store from '@/redux';
import { Provider } from 'react-redux';
import './App.scss';
import Providers from '@components/Providers.tsx';
import BaseLayout from '@pages/Layout';

function App() {
  useEffect(() => {
    document.title = `EventModel WebSocket v${import.meta.env.PACKAGE_VERSION}`;
  }, []);

  return (
    <Provider store={store}>
      <Providers>
        <BaseLayout />
      </Providers>
    </Provider>
  );
}

export default App;
