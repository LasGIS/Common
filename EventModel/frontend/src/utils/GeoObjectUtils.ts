import { Point } from '@/types/redux/ObjectsTypes.ts';

export const toPoint = (event: MouseEvent): Point => ({ x: event.offsetX, y: event.offsetY });
