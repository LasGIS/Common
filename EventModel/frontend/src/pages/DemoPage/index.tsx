// import './css/style.scss';
import React, { useRef } from 'react';
import { Link } from 'react-router-dom';
import useStompJsClient from '@/hooks/useStompJsClient.ts';
import { Client } from '@stomp/stompjs';
import { MainContent, Row } from '@/style/style.tsx';
import InlineForm from '@pages/DemoPage/components/InlineForm.tsx';

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
    <MainContent className="main-content">
      <h3>@pages\DemoPage.tsx</h3>
      <Row className="row">
        <InlineForm>
          <label htmlFor="connect">WebSocket connection:</label>
          <button id="connect" className="btn btn-default" type="submit">Connect</button>
          <button id="disconnect" className="btn btn-default" type="submit" disabled={true}>Disconnect</button>
        </InlineForm>
        <InlineForm>
          <label htmlFor="name">Сообщение:</label>
          <input type="text" id="name" className="form-control" placeholder="Your name here..." />
          <button className="btn btn-default" onClick={() => send({ name: 'message' })}>Послать</button>
          <Link to="/">Вернуться</Link>
        </InlineForm>
      </Row>
    </MainContent>
  );
};

export default DemoPage;
