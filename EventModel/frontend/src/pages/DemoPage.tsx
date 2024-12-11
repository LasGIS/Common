import React, { useRef } from 'react';
import { Link } from 'react-router-dom';
import useStompJsClient from '@/hooks/useStompJsClient.ts';
import { Client } from '@stomp/stompjs';

interface DemoMessageType {
  content: string;
}

interface DemoSendType {
  name: string;
}

const DemoPage: React.FC = () => {
  const ref = useRef<Client | null>(null);
  const { send } = useStompJsClient<DemoMessageType, DemoSendType>(ref, {
    url: 'ws://localhost:8088/gs-guide-websocket',
    subscribeDestination: '/topic/greetings',
    publishDestination: '/app/hello',
    onMessage: (message) => {
      console.log(`message: ${message.content}`);
    },
    debug: false,
  });

  return (
    <div>
      <h2>@pages\DemoPage.tsx</h2>
      <button className="btn btn-default" onClick={() => send({ name: 'message' })}>Послать</button>
      <Link to="/">Вернуться</Link>
    </div>
  );
};

export default DemoPage;
