import { Canvas } from '@/canvas/Canvas.ts';
import { Point } from '@/types/redux/ObjectsTypes.ts';

export type DrawFunction = (ctx: CanvasRenderingContext2D, canvas: Canvas, mousePnt?: Point) => void;
