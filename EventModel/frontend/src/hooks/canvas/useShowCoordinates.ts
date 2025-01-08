import { Canvas } from '@/canvas/Canvas.ts';
import { useCanvasEvent } from '@/hooks/canvas/useCanvasEvent.ts';

const TEXT_MARGIN_WIDTH = 10;
const TEXT_MARGIN_HEIGHT = 5;

const useShowCoordinates = (canvas: Canvas | null) => {
  useCanvasEvent('mousemove', canvas, (event: MouseEvent, canvas: Canvas) => {
    const text = `type: ${event.type}, x:${event.offsetX}, y:${event.offsetY}`;
    if (canvas) {
      const ctx = canvas.ctx;
      ctx.save();
      ctx.font = '14px Roboto, sans-serif';
      ctx.textAlign = 'center';
      const metrics: TextMetrics = ctx.measureText(text);
      const width = metrics.width + TEXT_MARGIN_WIDTH * 2;
      const height = metrics.fontBoundingBoxAscent + TEXT_MARGIN_HEIGHT * 2;
      ctx.fillStyle = 'rgb(200, 200, 200)';
      ctx.fillRect(10, 10, width, height);
      ctx.fillStyle = 'black';
      ctx.fillText(text, 10 + width / 2, 10 + metrics.actualBoundingBoxAscent + TEXT_MARGIN_HEIGHT);
      ctx.restore();
    }
  });
};

export default useShowCoordinates;
