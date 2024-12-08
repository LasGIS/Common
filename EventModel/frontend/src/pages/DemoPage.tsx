import React from 'react';
import useWebsocket from '@/hooks/useWebsocket.ts';
import { Link } from 'react-router-dom';

const DemoPage: React.FC = () => {
  const { send, close } = useWebsocket<string, string>({
    url: 'ws://localhost:8088/gs-guide-websocket',
    onMessage: (message) => {
      console.log(message);
    },
  });

  return (
    <div>
      <h1>@pages\DemoPage.tsx</h1>
      <button className="btn btn-default" onClick={() => send('message')}>Послать</button>
      <button className="btn btn-default" onClick={() => close(1000, 'так надо')}>Закрыть</button>
      <Link to="/">Вернуться</Link>
    </div>
  );
};

export default DemoPage;
