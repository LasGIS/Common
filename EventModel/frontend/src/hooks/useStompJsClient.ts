import { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import { IFrame } from '@stomp/stompjs/src/i-frame.ts';

interface WebsocketInputType<MessageType> {
  url: string;
  onOpen?: (ev: Event) => void;
  onClose?: (ev: CloseEvent) => void;
  onError?: (ev: Event) => void;
  onMessage?: (message: MessageType) => void;
}

interface WebsocketOutputType<SendType> {
  connect: () => void;
  close: () => Promise<void>;
  send: (message: SendType) => void;
}

const useStompJsClient = <MessageType, SendType>({
  url, onMessage,
}: WebsocketInputType<MessageType>): WebsocketOutputType<SendType> => {
  const [ client, setClient ] = useState<Client | null>(null);

  useEffect(() => {
    if (!client) {
      const client = new Client({
        brokerURL: url,
        onConnect: () => {
          client.subscribe('/topic/greetings', (greeting) => {
            if (onMessage) {
              onMessage(JSON.parse(greeting.body).content as MessageType);
            }
            // console.log(`message: ${JSON.stringify(greeting, null, 2)}`);
          });
        },
        onDisconnect: (frame: IFrame) => {
          console.log(`onDisconnect: ${JSON.stringify(frame)}`);
        },
        onChangeState: (state) => {
          console.log(`onChangeState: ${JSON.stringify(state)}`);
        },
        onStompError: (frame: IFrame) => {
          console.log(`onStompError: ${JSON.stringify(frame)}`);
        },
      });
      setClient(client);
    }

    return () => {
      client?.deactivate().then(() => {
        console.log('client.deactivate ПРИ закрытии');
      });
    };
  }, []);

  const connect = () => {
    client?.activate();
  };

  const close = async () => {
    return client?.deactivate().then(() => {
      console.log('client.deactivate ПО кнопке');
    });
  };

  const send = (message: SendType) => {
    return client?.publish({
      destination: '/app/hello',
      body: JSON.stringify({ name: message }),
    });
  };

  return { connect, close, send };
};

export default useStompJsClient;
