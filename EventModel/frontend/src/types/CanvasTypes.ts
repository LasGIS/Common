import { Canvas } from '@/canvas/Canvas.ts';
import { Point } from '@/types/redux/ObjectsTypes.ts';

export type DrawFunction = (ctx: CanvasRenderingContext2D, canvas: Canvas, mousePnt?: Point) => void;

export type CanvasType = 'back' | 'scale' | 'main' | 'work' | 'info';

export interface CanvasInitial {
  type: CanvasType;
  zIndex: number;
}
