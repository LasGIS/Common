import { describe, expect, test } from '@jest/globals';

describe('Generator', () => {
  test('NON security formatTelNumber()', () => {
    function* gen() {
      // Передаём вопрос во внешний код и ожидаем ответа
      let result = yield '2 + 2 = ?'; // (*)
      console.log(`Last result = ${result}`);
      expect(result).toEqual([4, 5]);
      result = yield '3 * 3 = ?'; // (*)
      console.log(`Last result = ${JSON.stringify(result, null, 3)}`);
      expect(result).toEqual({ result: 9 });
    }

    const generator = gen();

    let question = generator.next('ignore').value;
    expect(question).toBe('2 + 2 = ?');

    question = generator.next([4, 5]).value;
    expect(question).toBe('3 * 3 = ?');

    question = generator.next({ result: 9 }); // --> передаём результат в генератор
    expect(question).toEqual({ done: true, value: undefined });
  });
});
