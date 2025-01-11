import React from 'react';
import styled from 'styled-components';
import useCanvas from '@/hooks/canvas/useCanvas.ts';

const CanvasWrapper = styled.div`
  width: 100vw;
  height: calc(100vh - 68px);
  background-color: #fff;
`;

const MainPage: React.FC = () => {
  const { containerRef, canvas } = useCanvas();
  console.log(`canvas: ${canvas?.width} x ${canvas?.height}`);

  return (
    <CanvasWrapper>
      <canvas ref={containerRef}></canvas>
    </CanvasWrapper>
  );
};

export default MainPage;
