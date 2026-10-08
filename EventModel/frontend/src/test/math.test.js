import { describe, test, expect } from 'vitest';
import { sum, divide } from './math';

describe('Тестирование математических функций', () => {
  test('Сложение: должен правильно складывать два числа', () => {
    const result = sum(2, 3);
    expect(result).toBe(5);
  });

  test('Деление: должен корректно делить числа', () => {
    expect(divide(6, 2)).toBe(3);
  });

  test('Деление: должен выбрасывать ошибку при делении на ноль', () => {
    // expect(() => divide(5, 0)).toBe('Деление на ноль невозможно');
    expect(() => divide(5, 0)).toThrow('Деление на ноль невозможно');
  });
});
