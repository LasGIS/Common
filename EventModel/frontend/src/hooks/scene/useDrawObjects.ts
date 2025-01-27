import { useEffect } from 'react';
import { objectsSelector } from '@/redux/reducer/ObjectsReducer.ts';
import { GeoObject, Point } from '@/types/redux/ObjectsTypes.ts';
import { useAppSelector } from '@/redux';
import { drawCirclePoint } from '@/canvas/canvasUtils.ts';
import { Scene } from '@/canvas/Scene.ts';
import { useSceneEvent } from '@/hooks/scene/useSceneEvent.ts';
import { Canvas } from '@/canvas/Canvas.ts';
import { toPoint } from '@/utils/GeoObjectUtils.ts';

interface Prop {
  scene: Scene;
}

const useDrawObjects = ({ scene }: Prop) => {
  const objects = useAppSelector(objectsSelector);

  useEffect(() => {
    const canvas = scene.getCanvas('main');
    canvas.addDraw('2-Objects', draw);
    canvas.draw();

    return () => {
      canvas.removeDraw('2-Objects');
    };
  }, [scene]);

  useEffect(() => {
    const canvas = scene.getCanvas('main');
    canvas.addDraw('2-Objects', draw);
    canvas.draw();
  }, [objects]);

  const draw = (ctx: CanvasRenderingContext2D, _, mousePnt?: Point) => {
    Object.values(objects).forEach((geo: GeoObject) => {
      ctx.beginPath();
      geo.polygon.forEach((pnt, index) => {
        if (index === 0) {
          ctx.moveTo(pnt.x, pnt.y);
        } else {
          ctx.lineTo(pnt.x, pnt.y);
        }
      });
      ctx.closePath();
      const isInPath = mousePnt && ctx.isPointInPath(mousePnt.x, mousePnt.y, 'evenodd');
      const isInStroke = mousePnt && ctx.isPointInStroke(mousePnt.x, mousePnt.y);
      if (geo.fillStyle) {
        ctx.fillStyle = isInPath ? geo.fillStyle + 'ff' : geo.fillStyle + '80';
        ctx.fill('evenodd');
      }
      if (geo.strokeStyle) {
        ctx.strokeStyle = isInStroke ? geo.strokeStyle + 'ff' : geo.strokeStyle + '80';
        if (geo.lineWidth) {
          ctx.lineWidth = geo.lineWidth;
        }
        ctx.stroke();
      }
      geo.polygon.forEach((pnt) => {
        if (mousePnt && Math.abs(pnt.x - mousePnt.x) < 50 && Math.abs(pnt.y - mousePnt.y) < 50) {
          drawCirclePoint(ctx, pnt, 'rgb(255,100,200)');
        }
      });
    });
  };

  useSceneEvent('mousemove', 'main', scene, (event: MouseEvent, canvas: Canvas) => {
    const pnt: Point = toPoint(event);
    canvas.draw(pnt);
  });
};

export default useDrawObjects;
