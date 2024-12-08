import { ArrayQueue, ConstantBackoff, Websocket, WebsocketBuilder } from 'websocket-ts';
import { useEffect, useState } from 'react';

interface WebsocketInputType<MessageType> {
  url: string;
  onOpen?: (ev: Event) => void;
  onClose?: (ev: CloseEvent) => void;
  onError?: (ev: Event) => void;
  onMessage?: (message: MessageType) => void;
}

interface WebsocketOutputType<SendType extends string | ArrayBufferLike | Blob | ArrayBufferView> {
  close: (code: number, reason: string) => void;
  send: (message: SendType) => void;
}

const useWebsocket = <MessageType, SendType extends string | ArrayBufferLike | Blob | ArrayBufferView>({
  url, onOpen, onClose, onError, onMessage,
}: WebsocketInputType<MessageType>): WebsocketOutputType<SendType> => {
  const [ websocket, setWebsocket ] = useState<Websocket | undefined>();

  useEffect(() => {
    /** Initialize WebSocket with buffering and 1s reconnection delay */
    const websocket: Websocket = new WebsocketBuilder(url)
      .withBuffer(new ArrayQueue())           // buffer messages when disconnected
      .withBackoff(new ConstantBackoff(10000)) // retry every 1s
      .onOpen((_, ev) => {
        if (onOpen) {
          onOpen(ev);
        }
        console.log('WebSocket opened');
      })
      .onClose((_, ev) => {
        if (onClose) {
          onClose(ev);
        }
        console.log('WebSocket closed');
      })
      .onError((_, ev) => {
        if (onError) {
          onError(ev);
        }
        console.log('WebSocket error');
      })
      .onMessage((_, ev: MessageEvent<MessageType>) => {
        if (onMessage) {
          onMessage(ev.data);
        }
        console.log('message');
      })
      .onRetry((/*i, ev*/) => console.log('retry'))
      .onReconnect((/*i, ev*/) => console.log('reconnect'))
      .build();
    setWebsocket(websocket);
  }, []);

  const close = (code: number, reason: string) => {
    websocket?.close(code, reason);
  };

  const send = (message: SendType) => {
    return websocket?.send(message);
  };

  return { close, send };
};

export default useWebsocket;
