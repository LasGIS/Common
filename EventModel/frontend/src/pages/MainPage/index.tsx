import React, { useRef } from 'react';
import styled from 'styled-components';
import useCanvas from '@/hooks/canvas/useCanvas.ts';

const CanvasWrapper = styled.div`
  width: 100vw;
  height: calc(100vh - 68px);
  background-color: #fff;
`;

const MainPage: React.FC = () => {
  const canvasContainerRef = useRef<HTMLCanvasElement | null>(null);
  useCanvas(canvasContainerRef);

  return (
    <CanvasWrapper>
      <canvas ref={canvasContainerRef} id="canvas" width={3000} height={1500}></canvas>
    </CanvasWrapper>
  );
};

export default MainPage;
