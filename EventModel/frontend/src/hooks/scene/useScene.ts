import { RefCallback, useCallback, useState } from 'react';
import { Scene } from '@/canvas/Scene.ts';
import useShowCoordinates from '@/hooks/scene/useShowCoordinates.ts';
import { useAppDispatch } from '@/redux';
import { addGeoObject } from '@/redux/reducer/ObjectsReducer.ts';
import useEditObject from '@/hooks/scene/useEditObject.ts';
import useResizeObserver from '@react-hook/resize-observer';
import { useSceneEvent } from '@/hooks/scene/useSceneEvent.ts';

export const useScene = (): {
  scene: Scene | null;
  containerRef: RefCallback<HTMLElement>;
} => {
  const [scene, setScene] = useState<Scene | null>(null);
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
  const containerRef = useCallback((element: HTMLElement | null) => {
    if (element !== null) {
      console.log(`Монтируем new Scene(${element})`);
      const initial = new Scene(element, [
        // { type: 'back', zIndex: 1000 },
        // { type: 'scale', zIndex: 1010 },
        { type: 'main', zIndex: 1020 },
        { type: 'work', zIndex: 1030 },
        { type: 'info', zIndex: 1040 },
      ]);
      addMockGeoObject();
      setScene(initial.resize());
    } else {
      scene?.clear();
      console.log(`Размонтируем old scene = ${scene}`);
      setScene(null);
    }
    return () => {
      console.log(`Размонтируем scene = ${scene}`);
      scene?.clear();
    };
  }, []);

  useResizeObserver(scene?.container, (entry) => {
    const { width, height } = entry.contentRect;
    scene?.getAllCanvases().forEach((canvas) => canvas.setSize(width, height));
  });

  console.log('---=== useScene ===---');
  useSceneEvent('contextmenu', 'main', scene, (e) => e.preventDefault());
  // useCorrectOnResize(scene);
  useShowCoordinates(scene);
  // useDrawObjects(scene);
  useEditObject(scene);

  return { containerRef, scene };
};

export default useScene;
