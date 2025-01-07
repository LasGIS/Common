import React, { ChangeEvent, useRef, useState } from 'react';
import useStompJsClient from '@/hooks/websocket/useStompJsClient.ts';
import { Client } from '@stomp/stompjs';
import { MainContent, Row } from '@/style/style.tsx';
import List from '@pages/DemoPage/components/List.tsx';

interface DemoMessageType {
  content: string;
}

interface DemoSendType {
  name: string;
}

const DemoPage: React.FC = () => {
  const ref = useRef<Client | null>(null);
  const [name, setName] = useState<string>('');
  const [list, setList] = useState<string[]>([]);

  const { send } = useStompJsClient<DemoMessageType, DemoSendType>(ref, {
    url: 'ws://localhost:8088/gs-guide-websocket',
    subscribeDestination: '/topic/greetings',
    publishDestination: '/app/hello',
    onMessage: (message) => {
      list.push(message.content);
      setList([...list]);
      console.log(`message: ${message.content}`);
    },
    debug: false,
  });

  const onChange = (event: ChangeEvent<HTMLInputElement>) => {
    setName(event.target.value);
  };

  return (
    <MainContent>
      <Row>
        <label htmlFor="connect">Connection:</label>
        <button id="connect" type="submit">
          Connect
        </button>
        <button id="disconnect" type="submit" disabled={true}>
          Disconnect
        </button>
        <label htmlFor="name">Сообщение:</label>
        <input type="text" id="name" placeholder="Your name here..." onChange={onChange} />
        <button type="button" onClick={() => send({ name })}>
          Послать
        </button>
      </Row>
      <List list={list} />
    </MainContent>
  );
};

export default DemoPage;
