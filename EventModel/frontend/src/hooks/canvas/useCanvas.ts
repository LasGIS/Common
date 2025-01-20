import { RefCallback, useCallback, useState } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';
import useShowCoordinates from '@/hooks/canvas/useShowCoordinates.ts';
import { useAppDispatch } from '@/redux';
import { addGeoObject } from '@/redux/reducer/ObjectsReducer.ts';
import useEditObject from '@/hooks/canvas/useEditObject.ts';
import useResizeObserver from '@react-hook/resize-observer';
import { useCanvasEvent } from '@/hooks/canvas/useCanvasEvent.ts';

export const useCanvas = (): {
  canvas: Canvas | null;
  containerRef: RefCallback<HTMLCanvasElement>;
} => {
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

  /**
   * Используем useCallback для получения DOM элемента.
   * Для этого containerRef подкладываем в ref элемента. Например: <pre>
   *     <canvas ref={containerRef}></canvas>
   * </pre>
   * containerRef функция гарантированно будет вызвана при монтировании и размонтировании элементов.
   *
   * @param element при монтировании возвращается объект HTMLElement,</br>
   * при размонтировании элементов - null
   * @return callback функция, которую надо положить в ref элемента
   */
  const containerRef = useCallback((element: HTMLCanvasElement | null) => {
    if (element !== null) {
      console.log(`Монтируем new Canvas(${element})`);
      const initial = new Canvas(element);
      addMockGeoObject();
      setCanvas(initial.resize());
    } else {
      console.log(`Размонтируем old canvas = ${canvas}`);
      setCanvas(null);
    }
  }, []);

  useResizeObserver(canvas?.canvasElement.parentElement, (entry) => {
    const { width, height } = entry.contentRect;
    canvas?.setSize(width, height);
  });

  console.log('---=== useCanvas ===---');
  useCanvasEvent('contextmenu', canvas, (e) => e.preventDefault());
  // useCorrectOnResize(canvas);
  useShowCoordinates(canvas);
  // useDrawObjects(canvas);
  useEditObject(canvas);

  return { containerRef, canvas };
};

export default useCanvas;
