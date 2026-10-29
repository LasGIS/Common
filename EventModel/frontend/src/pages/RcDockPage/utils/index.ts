import { BoxBase, LayoutBase } from 'rc-dock';

const EXCLUDE_KEYS = ['parent', 'content', 'title'];

export function removeCyclicReferences<T extends LayoutBase | BoxBase>(obj: T): T {
  const seen = new WeakSet();
  return JSON.parse(
    JSON.stringify(obj, (key, value) => {
      if (typeof value === 'object' && value !== null) {
        if (EXCLUDE_KEYS.includes(key)) {
          return;
        }
        if (seen.has(value)) {
          return '[Circular]'; // Разрываем цикл, заменяя повтор на строку
        }
        seen.add(value);
      }
      return value;
    })
  );
}
