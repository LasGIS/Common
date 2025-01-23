import type { DrawFunction } from '@/types/CanvasTypes.ts';
import { Point } from '@/types/redux/ObjectsTypes.ts';

export class Canvas {
  public readonly canvasElement: HTMLCanvasElement;
  private drawFunctionMap: Record<string, DrawFunction> = {};

  constructor(canvasElement: HTMLCanvasElement) {
    this.canvasElement = canvasElement;
    this.addDraw('0-start', (ctx: CanvasRenderingContext2D, cnv: Canvas) => {
      ctx.clearRect(0, 0, cnv.width, cnv.height);
      // ctx.fillStyle = '#ffffffff';
      // ctx.fillRect(10, 10, cnv.width - 20, cnv.height - 20);
    });
  }

  public get width(): number {
    return this.canvasElement.width;
  }

  public get height(): number {
    return this.canvasElement.height;
  }

  public get ctx(): CanvasRenderingContext2D {
    return this.canvasElement.getContext('2d');
  }

  public addDraw(key: string, drawFun: DrawFunction) {
    this.drawFunctionMap[key] = drawFun;
  }

  public removeDraw(key: string) {
    delete this.drawFunctionMap[key];
  }

  public draw(mousePnt?: Point): CanvasRenderingContext2D {
    Object.keys(this.drawFunctionMap)
      .sort()
      .forEach((key) => {
        this.drawFunctionMap[key](this.ctx, this, mousePnt);
      });
    return this.ctx;
  }

  public resize(): Canvas {
    if (this.canvasElement && this.canvasElement.parentElement) {
      const parentElement = this.canvasElement.parentElement;
      this.canvasElement.width = parentElement.clientWidth;
      this.canvasElement.height = parentElement.clientHeight;
    }
    return this;
  }

  public setSize(width: number, height: number): Canvas {
    this.canvasElement.width = width;
    this.canvasElement.height = height;
    this.draw();
    return this;
  }

  public addEventListener<Type extends keyof HTMLElementEventMap>(
    type: Type,
    handler: (event: HTMLElementEventMap[Type], canvas: Canvas) => unknown
  ): Canvas {
    console.log(`canvas.addEventListener('${type}', ${handler.name});`);
    const handle = (event: HTMLElementEventMap[Type]) => handler(event, this);
    this.canvasElement.addEventListener(type, handle);
    return this;
  }

  public removeEventListener<Type extends keyof HTMLElementEventMap>(
    type: Type,
    handler: (event: HTMLElementEventMap[Type], canvas: Canvas) => unknown
  ): Canvas {
    console.log(`canvas.removeEventListener('${type}', ${handler.name});`);
    const handle = (event: HTMLElementEventMap[Type]) => handler(event, this);
    this.canvasElement.removeEventListener(type, handle);
    return this;
  }
}
