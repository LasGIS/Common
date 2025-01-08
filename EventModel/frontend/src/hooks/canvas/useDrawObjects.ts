import { Canvas } from '@/canvas/Canvas.ts';
import { useEffect } from 'react';
import { objectsSelector } from '@/redux/reducer/ObjectsReducer.ts';
import { useSelector } from 'react-redux';
import { GeoObject } from '@/types/redux/ObjectsTypes.ts';

const useDrawObjects = (canvas: Canvas | null) => {
  const objects = useSelector(objectsSelector);

  useEffect(() => {
    if (canvas !== null) {
      canvas.addDraw('2-Objects', draw);
      canvas.draw();

      return () => {
        canvas.removeDraw('2-Objects');
      };
    }
  }, [canvas]);

  useEffect(() => {
    if (canvas !== null) {
      canvas.addDraw('2-Objects', draw);
      canvas.draw();
    }
  }, [objects]);

  const draw = (ctx: CanvasRenderingContext2D) => {
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
      if (geo.fillStyle) {
        ctx.fillStyle = geo.fillStyle;
        ctx.fill();
      }
      if (geo.strokeStyle) {
        ctx.strokeStyle = geo.strokeStyle;
        if (geo.lineWidth) {
          ctx.lineWidth = geo.lineWidth;
        }
        ctx.stroke();
      }
    });
  };
};

export default useDrawObjects;
