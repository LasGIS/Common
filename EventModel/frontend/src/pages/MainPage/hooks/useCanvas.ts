import { MutableRefObject, useEffect, useState } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';
// import { useAppDispatch } from '@/redux';

export const useCanvas = (containerRef: MutableRefObject<HTMLCanvasElement | null>): Canvas | undefined => {
  const [canvas, setCanvas] = useState<Canvas | undefined>();
  // const dispatch = useAppDispatch();

  useEffect(() => {
    console.log(`containerRef.current = ${containerRef.current}`);
    if (!canvas) {
      const initial = new Canvas(containerRef.current!);
      setCanvas(initial);
      initial.setDraw(draw);
      initial.resize();
      initial.draw();
    }
  }, []);

  useEffect(() => {
    if (canvas) {
      console.log("window.addEventListener('resize', onResize);");
      window.addEventListener('resize', onResize);
      canvas.addEventListener('mousemove', onMousemove);

      return () => {
        console.log("window.removeEventListener('resize', onResize);");
        window.removeEventListener('resize', onResize);
        canvas.removeEventListener('mousemove', onMousemove);
      };
    }
  }, [canvas]);

  const onResize = (event) => {
    console.log(`event: ${event}`);
    if (canvas) {
      canvas.resize();
      canvas.draw();
    }
  };

  const onMousemove = (event: MouseEvent) => {
    const text = `type: ${event.type}, x:${event.offsetX}, y:${event.offsetY}`;
    if (canvas) {
      const ctx = canvas.ctx;
      ctx.save();
      const metrics: TextMetrics = ctx.measureText(text);
      ctx.fillStyle = 'rgb(200, 200, 200)';
      ctx.clearRect(10, 10, metrics.width + 40, metrics.fontBoundingBoxAscent + 4);
      // ctx.beginPath();
      ctx.fillStyle = 'black';
      ctx.fillText(text, 10 + 2, 10 + metrics.fontBoundingBoxAscent);
      ctx.restore();
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
