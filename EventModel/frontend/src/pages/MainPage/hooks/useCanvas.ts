import { MutableRefObject, useEffect, useState } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';
// import { useAppDispatch } from '@/redux';

export const useCanvas = (containerRef: MutableRefObject<HTMLCanvasElement | null>): Canvas | undefined => {
  const [canvas, setCanvas] = useState<Canvas | undefined>();
  // const dispatch = useAppDispatch();

  useEffect(() => {
    const initial = new Canvas(containerRef.current!);
    if (initial) {
      setCanvas(initial);
      initial.setDraw(draw);
      initial.resize();
      initial.draw();
      window.addEventListener('resize', onResize);
    }
    return () => {
      console.log("window.removeEventListener('resize', onResize);");
      window.removeEventListener('resize', onResize);
    };
  }, [containerRef.current]);

  const onResize = () => {
    if (canvas) {
      canvas.resize();
      canvas.draw();
    }
  };

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

  return canvas;
};
