import { useEffect } from 'react';
import { Canvas } from '@/canvas/Canvas.ts';
import { Scene } from '@/canvas/Scene.ts';
import { CanvasType } from '@/types/CanvasTypes.ts';

export function useSceneEvent<Type extends keyof HTMLElementEventMap>(
  type: Type,
  canvasType: CanvasType,
  scene: Scene | null,
  handler: (event: HTMLElementEventMap[Type], canvas: Canvas) => unknown
) {
  useEffect(() => {
    if (scene) {
      console.log(`scene.addEventListener('${type}', '${canvasType}');`);
      const canvas: Canvas = scene.getCanvas(canvasType);
      if (canvas) {
        const handle = (event: HTMLElementEventMap[Type]) => handler(event, canvas);
        scene.container.addEventListener(type, handle);

        return () => {
          console.log(`scene.removeEventListener('${type}', '${canvasType}');`);
          scene.container.removeEventListener(type, handle);
        };
      }
    }
  }, [scene, handler]);
}
