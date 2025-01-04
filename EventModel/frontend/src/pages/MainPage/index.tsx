import React, { useEffect, useRef } from 'react';
import styled from 'styled-components';

const CanvasWrapper = styled.div`
  width: 100vw;
  height: calc(100vh - 68px);
  background-color: #fff;
`;

const MainPage: React.FC = () => {
  const ref = useRef<HTMLCanvasElement | null>(null);

  const resize = (canvas): { width: number; height: number } => {
    const width = canvas.parentElement.clientWidth;
    const height = canvas.parentElement?.clientHeight;
    canvas.width = width;
    canvas.height = height;
    return { width, height };
  };

  useEffect(() => {
    const onResize = () => {
      const canvas = ref.current;
      if (canvas) {
        const { width, height } = resize(canvas);
        const ctx = canvas.getContext('2d');
        ctx.fillStyle = '#c0c0c0';
        ctx.fillRect(10, 10, width - 20, height - 20);
      }
    };

    const canvas = ref.current;
    if (canvas) {
      console.log("window.addEventListener('resize', onResize);");
      window.addEventListener('resize', onResize);
      onResize();
    }

    return () => {
      console.log("window.removeEventListener('resize', onResize);");
      window.removeEventListener('resize', onResize);
    };
  }, [ref]);

  return (
    <CanvasWrapper>
      <canvas ref={ref} id="canvas" width={3000} height={1500}></canvas>
    </CanvasWrapper>
  );
};

export default MainPage;
