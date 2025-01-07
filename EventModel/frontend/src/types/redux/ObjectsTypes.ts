interface Point {
  x: number;
  y: number;
}

interface GeoObject {
  id: number;
  fillStyle?: CanvasFillStrokeStyles['fillStyle'];
  strokeStyle?: CanvasFillStrokeStyles['strokeStyle'];
  lineWidth?: number;
  polygon: Point[];
  selected?: boolean;
}

interface ObjectsState {
  objects: Record<number, GeoObject>;
  selected?: GeoObject;
}

export { ObjectsState, GeoObject, Point };
