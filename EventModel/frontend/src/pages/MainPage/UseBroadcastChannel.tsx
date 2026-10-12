import React from 'react';

const BC_START_WINDOW_EVENTS_NAME = 'start-window-events';

const UseBroadcastChannel = () => {
  const [broadcast] = React.useState<BroadcastChannel>(new BroadcastChannel(BC_START_WINDOW_EVENTS_NAME));

  const checkStartWindowEvent = (e: MessageEvent<string>) => {
    console.log(`checkStartWindowEvent: ${e.data}`);
  };

  React.useEffect(() => {
    console.log('UseBroadcastChannel: START');
    broadcast.addEventListener('message', checkStartWindowEvent);
    return () => {
      console.log('UseBroadcastChannel: FINISH');
      broadcast.removeEventListener('message', checkStartWindowEvent);
      broadcast.close();
    };
  }, []);
};

export default UseBroadcastChannel;
