import { MutableRefObject, useEffect, useState } from 'react';
import { ActivationState, Client } from '@stomp/stompjs';
import { IFrame } from '@stomp/stompjs/src/i-frame.ts';

const ActivationStateName = {
  0: 'ACTIVE',
  1: 'DEACTIVATING',
  2: 'INACTIVE',
};

interface StompJsClient<MessageType> {
  url: string;
  subscribeDestination: string;
  publishDestination: string;
  debug?: boolean;
  onOpen?: (ev: Event) => void;
  onClose?: (ev: CloseEvent) => void;
  onError?: (ev: Event) => void;
  onMessage?: (message: MessageType) => void;
}

interface StompJsClientOut<SendType> {
  send: (message: SendType) => void;
}

const useStompJsClient = <MessageType, SendType>(
  ref: MutableRefObject<Client | null>,
  { url, subscribeDestination, publishDestination, onMessage, debug = false }: StompJsClient<MessageType>,
): StompJsClientOut<SendType> => {

  useEffect(() => {
    if (!ref.current) {
      const client = new Client({
        brokerURL: url,
        onDisconnect: (frame: IFrame) => {
          console.log(`onDisconnect: ${JSON.stringify(frame)}`);
        },
        onChangeState: (state: ActivationState) => {
          console.log(`onChangeState: ${ActivationStateName[state]}`);
        },
        onWebSocketError: (frame: IFrame) => {
          console.log(`onWebSocketError: ${JSON.stringify(frame)}`);
        },
        onStompError: (frame: IFrame) => {
          console.log(`onStompError: ${JSON.stringify(frame)}`);
        },
      });
      ref.current = client;

      client.onConnect = () => {
        client.subscribe(subscribeDestination, (greeting) => {
          if (onMessage) {
            onMessage(JSON.parse(greeting.body) as MessageType);
          }
          //// console.log(`message: ${JSON.stringify(greeting, null, 2)}`);
        });
      };

      if (debug) {
        client.debug = (msg) => {
          console.log(`StompJsClient.debug: ${msg}`);
        };
      }

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

  const send = (message: SendType) => {
    return ref.current?.publish({
      destination: publishDestination,
      body: JSON.stringify(message)
    });
  };

  return { send };
};

export default useStompJsClient;
