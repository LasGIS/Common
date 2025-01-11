import { MutableRefObject, useEffect, useState } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';
import useShowCoordinates from '@/hooks/canvas/useShowCoordinates.ts';
import useCorrectOnResize from '@/hooks/canvas/useCorrectOnResize.ts';
import useDrawObjects from '@/hooks/canvas/useDrawObjects.ts';
import { useAppDispatch } from '@/redux';
import { addGeoObject } from '@/redux/reducer/ObjectsReducer.ts';
import useEditObject from '@/hooks/canvas/useEditObject.ts';

export const useCanvas = (containerRef: MutableRefObject<HTMLCanvasElement | null>): Canvas | null => {
  const [canvas, setCanvas] = useState<Canvas | null>(null);
  const dispatch = useAppDispatch();

  function addMockGeoObject() {
    let id = 1;
    for (let i = 0, x = 50; i < 4; i++, x += 120) {
      for (let j = 0, y = 50; j < 3; j++, y += 120) {
        dispatch(
          addGeoObject({
            id: id++,
            polygon: [
              { x, y },
              { x: x + 100, y },
              { x: x + 80, y: y + 80 },
              { x, y: y + 90 },
            ],
            fillStyle: '#5fffff',
            lineWidth: 3,
            strokeStyle: '#ffafaf',
          })
        );
      }
    }
  }

  useEffect(() => {
    const can = document.getElementById('canvas');
    console.log(`useEffect(containerRef.current: ${containerRef.current}),
      document.getElementById('canvas') => ${can ? can.dir : can}
      `);
    if (containerRef.current !== null && canvas === null) {
      console.log('const initial = new Canvas(containerRef.current!);');
      const initial = new Canvas(containerRef.current as HTMLCanvasElement);
      addMockGeoObject();
      setCanvas(initial.resize());
    }
  }, [containerRef.current]);

  console.log('---=== useCanvas ===---');
  useCorrectOnResize(canvas);
  useShowCoordinates(canvas);
  useDrawObjects(canvas);
  useEditObject(canvas);

  return canvas;
};

export default useCanvas;
