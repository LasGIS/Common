import { Point } from '@/types/redux/ObjectsTypes.ts';

export const drawRectPoint = (
  ctx: CanvasRenderingContext2D,
  pnt: Point,
  fillStyle?: CanvasFillStrokeStyles['fillStyle']
) => {
  ctx.fillStyle = fillStyle ?? '#112d9b';
  ctx.fillRect(pnt.x - 3, pnt.y - 3, 6, 6);
};

export const drawCirclePoint = (
  ctx: CanvasRenderingContext2D,
  pnt: Point,
  fillStyle?: CanvasFillStrokeStyles['fillStyle']
) => {
  ctx.beginPath();
  ctx.fillStyle = fillStyle ?? '#119b18';
  ctx.arc(pnt.x, pnt.y, 5, 0, Math.PI * 2);
  ctx.closePath();
  ctx.fill();
};
