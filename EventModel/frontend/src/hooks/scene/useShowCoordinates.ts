import { Canvas } from '@/canvas/Canvas.ts';
import { useSceneEvent } from '@/hooks/scene/useSceneEvent.ts';
import { Scene } from '@/canvas/Scene.ts';
import { toPoint } from '@/utils/GeoObjectUtils.ts';

const TEXT_MARGIN_WIDTH = 10;
const TEXT_MARGIN_HEIGHT = 5;

const useShowCoordinates = (scene: Scene | null) => {
  useSceneEvent('mousemove', 'info', scene, (event: MouseEvent, canvas: Canvas) => {
    const mousePnt = toPoint(event);
    const text = `type: ${event.type}, x:${mousePnt.x}, y:${mousePnt.y}`;
    const ctx = canvas.ctx;
    ctx.save();
    ctx.font = '14px Roboto, sans-serif';
    ctx.textAlign = 'center';
    const maxWidth = canvas.width;
    const maxHeight = canvas.height;
    const metrics: TextMetrics = ctx.measureText(text);
    const width = metrics.width + TEXT_MARGIN_WIDTH * 2;
    const height = metrics.fontBoundingBoxAscent + TEXT_MARGIN_HEIGHT * 2;
    ctx.fillStyle = '#e0e0e0';
    ctx.fillRect(maxWidth - width, maxHeight - height, width, height);
    ctx.fillStyle = 'black';
    ctx.fillText(text, maxWidth - width / 2, maxHeight - height + metrics.actualBoundingBoxAscent + TEXT_MARGIN_HEIGHT);
    ctx.restore();
  });
};

export default useShowCoordinates;
