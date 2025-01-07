import { Canvas } from '@/canvas/Canvas.ts';

export type DrawFunction = (ctx: CanvasRenderingContext2D, canvas: Canvas) => void;
