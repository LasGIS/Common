import { useEffect } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';

export function useCanvasEvent<Type extends keyof HTMLElementEventMap>(
  type: Type,
  canvas: Canvas | null,
  handler: (event: HTMLElementEventMap[Type], canvas: Canvas) => unknown
) {
  useEffect(() => {
    if (canvas) {
      console.log(`canvas.addEventListener('${type}', ${handler.name});`);
      const handle = (event: HTMLElementEventMap[Type]) => handler(event, canvas);
      canvas.canvasElement.addEventListener(type, handle);

      return () => {
        console.log(`canvas.removeEventListener('${type}', ${handler.name});`);
        canvas.canvasElement.removeEventListener(type, handle);
      };
    }
  }, [canvas, handler]);
}
