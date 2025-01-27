import React from 'react';
import styled from 'styled-components';
import UseDrawObjects from '@/hooks/scene/useDrawObjects.ts';
import UseBroadcastChannel from '@pages/MainPage/UseBroadcastChannel.ts';
import useScene from '@/hooks/scene/useScene.ts';

const CanvasWrapper = styled.div`
  width: 100vw;
  height: calc(100vh - 68px);
  background-color: #ffffffff;
  text-align: left;
`;

const MainPage: React.FC = () => {
  const { containerRef, scene } = useScene();
  console.log(`scene: ${scene?.width} x ${scene?.height}`);

  return (
    <CanvasWrapper ref={containerRef}>
      {scene && <UseDrawObjects scene={scene} />}
      <UseBroadcastChannel />
    </CanvasWrapper>
  );
};

export default MainPage;
