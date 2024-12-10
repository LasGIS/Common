import { MutableRefObject, useEffect } from 'react';
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

const useStompJsClient = <MessageType, SendType>(ref: MutableRefObject<Client | null>, {
  url, onMessage,
}: WebsocketInputType<MessageType>): WebsocketOutputType<SendType> => {

  useEffect(() => {
    if (!ref.current) {
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
        debug: ((msg) => {
          console.log(`StompJsClient.debug: ${msg}`);
        }),
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
      ref.current = client;
      setTimeout(() => {
        ref.current?.activate();
      });
    }

    return () => {
      ref.current?.deactivate().then(() => {
        console.log('client.deactivate ПРИ закрытии');
      });
    };
  }, []);

  const connect = () => {
    ref.current?.activate();
  };

  const close = async () => {
    return ref.current?.deactivate().then(() => {
      console.log('client.deactivate ПО кнопке');
    });
  };

  const send = (message: SendType) => {
    return ref.current?.publish({
      destination: '/app/hello',
      body: JSON.stringify({ name: message }),
    });
  };

  return { connect, close, send };
};

export default useStompJsClient;
