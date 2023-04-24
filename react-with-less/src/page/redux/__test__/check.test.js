import { describe, expect, test } from '@jest/globals';

describe('Check reduce', () => {
  const list = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  test('Check reduce', () => {
    expect(
      list.reduce(
        (acc, value) => {
          acc.sum += value;
          return acc;
        },
        { sum: 0, name: 'Max' },
      ),
    ).toStrictEqual({ sum: 55, name: 'Max' });
  });
});
