import React, { useRef } from 'react';
import { Link } from 'react-router-dom';
import useStompJsClient from '@/hooks/useStompJsClient.ts';

const DemoPage: React.FC = () => {
  const ref = useRef(null);
  const { connect, send, close } = useStompJsClient<string, string>(ref, {
    url: 'ws://localhost:8088/gs-guide-websocket',
    onMessage: (message) => {
      console.log(message);
    },
  });

  return (
    <div>
      <h1>@pages\DemoPage.tsx</h1>
      <button className="btn btn-default" onClick={() => connect()}>Открыть</button>
      <button className="btn btn-default" onClick={() => send('message')}>Послать</button>
      <button className="btn btn-default" onClick={() => close()}>Закрыть</button>
      <Link to="/">Вернуться</Link>
    </div>
  );
};

export default DemoPage;
