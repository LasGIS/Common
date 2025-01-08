import { useEffect } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';
import { useCanvasEvent } from '@/hooks/canvas/useCanvasEvent.ts';

const useCorrectOnResize = (canvas: Canvas | null) => {
  useEffect(() => {
    if (canvas) {
      window.addEventListener('resize', onResize);

      return () => {
        window.removeEventListener('resize', onResize);
      };
    }
  }, [canvas]);

  const onResize = () => {
    if (canvas) {
      canvas.resize();
      canvas.draw();
    }
  };

  useCanvasEvent('contextmenu', canvas, (e) => e.preventDefault());
};

export default useCorrectOnResize;
