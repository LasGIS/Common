import { Canvas } from '@/canvas/Canvas.ts';
import type { CanvasType } from '@/types/CanvasTypes.ts';
import { CanvasInitial } from '@/types/CanvasTypes.ts';

export class Scene {
  private readonly container: HTMLElement;
  private readonly canvasMap: Partial<Record<CanvasType, Canvas>> = {};
  private mainCanvas: Canvas;

  constructor(container: HTMLElement, canvases: CanvasInitial[]) {
    this.container = container;
    if (!canvases.map((can) => can.type).includes('main')) {
      throw new Error('В сцене должен быть элемент "main"!');
    }
    canvases.forEach((init) => {
      const canvasElement = document.createElement('canvas');
      canvasElement.style.zIndex = `${init.zIndex}`;
      canvasElement.style.position = 'absolute';
      canvasElement.setAttribute('name', init.type);
      container.append(canvasElement);
      const canvas = new Canvas(canvasElement);
      this.canvasMap[init.type] = canvas;
      if (init.type === 'main') {
        this.mainCanvas = canvas;
      }
    });
  }

  public clear() {
    Object.keys(this.canvasMap).forEach((key) => {
      const canvas: Canvas = this.canvasMap[key];
      canvas.clear();
      delete this.canvasMap[key];
    });
    while (this.container.firstChild) {
      const canvasElement = this.container.firstChild;
      this.container.removeChild(canvasElement);
    }
  }

  public get width(): number {
    return this.container.clientWidth;
  }

  public get height(): number {
    return this.container.clientHeight;
  }

  public getCanvas(type: CanvasType): Canvas {
    return this.canvasMap[type] || this.mainCanvas;
  }

  public getAllCanvases(): Canvas[] {
    return Object.values(this.canvasMap);
  }

  public resize(): Scene {
    if (this.container) {
      this.getAllCanvases().forEach((canvas) => {
        canvas.canvasElement.width = this.container.clientWidth;
        canvas.canvasElement.height = this.container.clientHeight;
      });
    }
    return this;
  }
}
