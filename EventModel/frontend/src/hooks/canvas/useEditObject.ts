import { Canvas } from '@/canvas/Canvas.ts';
import { useRef } from 'react';
import { GeoObject, Point } from '@/types/redux/ObjectsTypes.ts';
import { useCanvasEvent } from '@/hooks/canvas/useCanvasEvent.ts';
import { toPoint } from '@/utils/GeoObjectUtils.ts';
import { useAddGeoObject } from '@/hooks/canvas/useAddGeoObject.ts';

const useEditObject = (canvas: Canvas | null) => {
  const geoRef = useRef<GeoObject>();
  const addGeoObject = useAddGeoObject();

  const drawRectPoint = (ctx, pnt: Point) => {
    ctx.fillStyle = '#112d9b';
    ctx.fillRect(pnt.x - 3, pnt.y - 3, 6, 6);
  };

  const drawCirclePoint = (ctx, pnt: Point) => {
    ctx.beginPath();
    ctx.fillStyle = '#119b18';
    ctx.arc(pnt.x, pnt.y, 5, 0, Math.PI * 2);
    ctx.closePath();
    ctx.fill();
  };

  const drawGeoObject = (ctx, geo: GeoObject) => {
    if (ctx && geo) {
      const polygon = geo.polygon;
      // ctx.save();
      // ctx.globalCompositeOperation = 'destination-in';
      if (polygon.length === 1) {
        drawRectPoint(ctx, polygon[0]);
      } else if (polygon.length > 1) {
        ctx.beginPath();
        polygon.forEach((pnt, index) => {
          if (index === 0) {
            ctx.moveTo(pnt.x, pnt.y);
          } else {
            ctx.lineTo(pnt.x, pnt.y);
          }
        });
        ctx.closePath();
        ctx.strokeStyle = geo.strokeStyle || '#222';
        ctx.lineWidth = geo.lineWidth || 1;
        ctx.stroke();

        polygon.forEach((pnt, index) => {
          if (index === 0) {
            drawRectPoint(ctx, pnt);
          } else {
            drawCirclePoint(ctx, pnt);
          }
        });
      }
      // ctx.restore();
    }
  };

  useCanvasEvent('mousedown', canvas, (event: MouseEvent) => {
    if ([0, 2].includes(event.button)) {
      const pnt: Point = toPoint(event);
      console.log(`onMouseDown ${JSON.stringify(pnt)}`);
      const geo = geoRef.current;
      if (geo !== undefined) {
        geo.polygon.push(pnt);
      } else {
        geoRef.current = {
          polygon: [pnt],
          lineWidth: 2,
          strokeStyle: '#afafff',
        };
      }
    }
  });

  useCanvasEvent('mouseup', canvas, (event: MouseEvent, canvas: Canvas) => {
    event.preventDefault();
    const pnt: Point = toPoint(event);
    const geo = geoRef.current;
    if (geo) {
      const last = geo.polygon.length - 1;
      canvas.draw();
      if (last >= 0) {
        geo.polygon[last] = pnt;
        drawGeoObject(canvas.ctx, geo);
      } else {
        drawRectPoint(canvas.ctx, pnt);
      }
      if ((event.button === 2 || event.ctrlKey) && last > 0) {
        console.log('onMouseUp event.button === 2');
        addGeoObject(geo);
        geoRef.current = undefined;
      } else {
        console.log(`onMouseUp event.button: ${event.button}, event.buttons: ${event.buttons}`);
      }
    }
  });

  useCanvasEvent('mousemove', canvas, (event: MouseEvent, canvas: Canvas) => {
    const pnt: Point = toPoint(event);
    const geo = geoRef.current;
    if (event.buttons > 0) {
      if (geo) {
        const last = geo.polygon.length - 1;
        canvas.draw();
        if (last >= 0) {
          geo.polygon[last] = pnt;
          drawGeoObject(canvas.ctx, geo);
        } else {
          drawRectPoint(canvas.ctx, pnt);
        }
      }
    }
  });
};

export default useEditObject;
