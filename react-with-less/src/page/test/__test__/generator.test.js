import { describe, expect, test } from '@jest/globals';

describe('Generator', () => {
  test('NON security formatTelNumber()', () => {
    function* gen() {
      // Передаём вопрос во внешний код и ожидаем ответа
      let result = yield '2 + 2 = ?'; // (*)
      console.log(`Last result = ${result}`);
      result = yield '3 * 3 = ?'; // (*)
      console.log(`Last result = ${result}`);
    }

    const generator = gen();

    let question = generator.next(45).value; // <-- yield возвращает значение
    expect(question).toBe('2 + 2 = ?');

    question = generator.next(4).value; // <-- yield возвращает значение
    expect(question).toBe('3 * 3 = ?');

    question = generator.next(9); // --> передаём результат в генератор
    expect(question).toEqual({ done: true, value: undefined });
  });
});
