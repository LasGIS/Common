import React from 'react';
import UseDrawObjects from '@/hooks/scene/useDrawObjects';
import UseBroadcastChannel from '@/pages/MainPage/UseBroadcastChannel';
import useScene from '@/hooks/scene/useScene';
import { MainPageWrapper } from '@/style/style';

const MainPage: React.FC = () => {
  const { containerRef, scene } = useScene();
  console.log(`scene: ${scene?.width} x ${scene?.height}`);

  return (
    <MainPageWrapper ref={containerRef}>
      {scene && <UseDrawObjects scene={scene} />}
      <UseBroadcastChannel />
    </MainPageWrapper>
  );
};

export default MainPage;
