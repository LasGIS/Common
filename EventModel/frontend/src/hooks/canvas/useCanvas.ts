import { MutableRefObject, useEffect, useState } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';
import useShowCoordinates from '@/hooks/canvas/useShowCoordinates.ts';
import useCorrectOnResize from '@/hooks/canvas/useCorrectOnResize.ts';
// import { useAppDispatch } from '@/redux';

export const useCanvas = (containerRef: MutableRefObject<HTMLCanvasElement | null>): Canvas | undefined => {
  const [canvas, setCanvas] = useState<Canvas | undefined>();
  // const dispatch = useAppDispatch();

  useEffect(() => {
    if (!canvas) {
      const initial = new Canvas(containerRef.current!);
      setCanvas(initial);
      initial.setDraw(draw);
      initial.resize();
      initial.draw();
    }
  }, []);

  const draw = (ctx: CanvasRenderingContext2D, cnv: Canvas) => {
    ctx.fillStyle = '#e0e0e0';
    ctx.fillRect(10, 10, cnv.width - 20, cnv.height - 20);

    ctx.fillStyle = '#a0ffff';
    ctx.beginPath();
    ctx.moveTo(50, 50);
    ctx.lineTo(50, 100);
    ctx.lineTo(200, 50);
    ctx.closePath();
    ctx.fill();
  };

  useCorrectOnResize(canvas);
  useShowCoordinates(canvas);

  return canvas;
};
