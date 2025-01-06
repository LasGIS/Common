export class Canvas {
  public readonly canvasElement: HTMLCanvasElement;
  private drawFun: (ctx: CanvasRenderingContext2D, canvas: Canvas) => void;

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
  }

  public setDraw(drawFun: (ctx: CanvasRenderingContext2D, canvas: Canvas) => void) {
    this.drawFun = drawFun;
  }

  public draw(): CanvasRenderingContext2D {
    this.drawFun(this.ctx, this);
    return this.ctx;
  }

  resize(): void {
    this.canvasElement.width = this.canvasElement.parentElement.clientWidth;
    this.canvasElement.height = this.canvasElement.parentElement?.clientHeight;
  }

  addEventListener<K extends keyof HTMLElementEventMap>(
    type: K,
    listener: (this: HTMLCanvasElement, ev: HTMLElementEventMap[K]) => unknown /*,
    options?: boolean | AddEventListenerOptions*/
  ): void {
    this.canvasElement.addEventListener(type, listener);
  }

  removeEventListener(
    type: string,
    listener: EventListenerOrEventListenerObject /*,
    options?: boolean | EventListenerOptions*/
  ): void {
    this.canvasElement.removeEventListener(type, listener);
  }
}
