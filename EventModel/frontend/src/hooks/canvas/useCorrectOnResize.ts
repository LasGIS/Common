import { useEffect } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';

const useCorrectOnResize = (canvas: Canvas | undefined) => {
  useEffect(() => {
    if (canvas) {
      // console.log("window.addEventListener('resize', onResize);");
      window.addEventListener('resize', onResize);

      return () => {
        // console.log("window.removeEventListener('resize', onResize);");
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
};

export default useCorrectOnResize;
