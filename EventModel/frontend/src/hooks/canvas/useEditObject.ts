import { Canvas } from '@/canvas/Canvas.ts';
import { useRef } from 'react';
import { GeoObject, Point } from '@/types/redux/ObjectsTypes.ts';
import { useCanvasEvent } from '@/hooks/canvas/useCanvasEvent.ts';
import { toPoint } from '@/utils/GeoObjectUtils.ts';
import { useAppDispatch } from '@/redux';
import { addGeoObject } from '@/redux/reducer/ObjectsReducer.ts';

const useEditObject = (canvas: Canvas | undefined) => {
  const dispatch = useAppDispatch();
  const geoRef = useRef<GeoObject>();

  const drawRectPoint = (ctx, pnt: Point) => {
    ctx.fillStyle = '#112d9b';
    ctx.fillRect(pnt.x - 2, pnt.y - 2, 5, 5);
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
        ctx.strokeStyle = geo.strokeStyle || '#222';
        ctx.lineWidth = geo.lineWidth || 1;
        ctx.stroke();
      }
      // ctx.restore();
    }
  };

  const onMouseDown = (event: MouseEvent) => {
    if (event.buttons === 1) {
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
  };

  const onMouseUp = (event: MouseEvent, canvas: Canvas) => {
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
      if (event.button === 0) {
        console.log(`onMouseUp event.button === 1 ${JSON.stringify(pnt)}`);
      } else if (event.button === 2) {
        console.log('onMouseUp event.button === 2');
        dispatch(addGeoObject(geo));
        geoRef.current = undefined;
      } else {
        console.log(`onMouseUp event.button: ${event.button}, event.buttons: ${event.buttons}`);
      }
    }
  };

  const onMouseMove = (event: MouseEvent, canvas: Canvas) => {
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
  };

  useCanvasEvent('mousedown', canvas, onMouseDown);
  useCanvasEvent('mouseup', canvas, onMouseUp);
  useCanvasEvent('mousemove', canvas, onMouseMove);
};

export default useEditObject;
