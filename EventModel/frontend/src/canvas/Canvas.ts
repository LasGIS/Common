import type { DrawFunction } from '@/types/CanvasTypes.ts';

export class Canvas {
  public readonly canvasElement: HTMLCanvasElement;
  private drawFunctionMap: Record<string, DrawFunction> = {};

  public get width(): number {
    return this.canvasElement.width;
  }

  public get height(): number {
    return this.canvasElement.height;
  }

  public get ctx(): CanvasRenderingContext2D {
    return this.canvasElement.getContext('2d');
  }

  constructor(canvasElement: HTMLCanvasElement) {
    this.canvasElement = canvasElement;
    this.addDraw('0-start', (ctx: CanvasRenderingContext2D, cnv: Canvas) => {
      ctx.fillStyle = '#e0e0e0';
      ctx.fillRect(10, 10, cnv.width - 20, cnv.height - 20);
    });
  }

  public addDraw(key: string, drawFun: DrawFunction) {
    this.drawFunctionMap[key] = drawFun;
  }

  public removeDraw(key: string) {
    delete this.drawFunctionMap[key];
  }

  public draw(): CanvasRenderingContext2D {
    Object.keys(this.drawFunctionMap)
      .sort()
      .forEach((key) => {
        this.drawFunctionMap[key](this.ctx, this);
      });
    return this.ctx;
  }

  resize(): Canvas {
    this.canvasElement.width = this.canvasElement.parentElement.clientWidth;
    this.canvasElement.height = this.canvasElement.parentElement?.clientHeight;
    return this;
  }
}
