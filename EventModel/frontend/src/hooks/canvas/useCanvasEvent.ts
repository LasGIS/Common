import { useEffect } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';

export function useCanvasEvent<Type extends keyof HTMLElementEventMap>(
  type: Type,
  canvas: Canvas | undefined,
  handler: (event: HTMLElementEventMap[Type], canvas: Canvas) => unknown
) {
  useEffect(() => {
    if (canvas) {
      const handle = (event: HTMLElementEventMap[Type]) => handler(event, canvas);
      canvas.addEventListener(type, handle);

      return () => {
        canvas.removeEventListener(type, handle);
      };
    }
  }, [canvas, handler]);
}
