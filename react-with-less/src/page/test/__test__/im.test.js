import { describe, expect, test } from '@jest/globals';
import { groupByField } from '../imTesting.ts';
import { arr1 } from '../data';
import { groupByFieldJs, groupByFieldReduce } from '../ImTestingReduce.js';

const resultMap = new Map([
  [
    'Russia',
    new Map([
      [1, { age: 20, country: 'Russia', name: 'Иван', registred: true }],
      [3, { age: 25, country: 'Russia', name: 'Леха', registred: false }],
      [5, { age: 30, country: 'Russia', name: 'Иван', registred: true }],
      [6, { age: 50, country: 'Russia', name: 'Леха', registred: true }],
    ]),
  ],
  [
    'USA',
    new Map([
      [2, { age: 30, country: 'USA', name: 'Дима', registred: true }],
      [4, { age: 20, country: 'USA', name: 'Леха', registred: false }],
    ]),
  ],
  ['Usa', new Map([[7, { age: 20, country: 'Usa', name: 'Дима', registred: false }]])],
]);

const resultObject = {
  Russia: {
    1: { age: 20, country: 'Russia', name: 'Иван', registred: true },
    3: { age: 25, country: 'Russia', name: 'Леха', registred: false },
    5: { age: 30, country: 'Russia', name: 'Иван', registred: true },
    6: { age: 50, country: 'Russia', name: 'Леха', registred: true },
  },
  USA: {
    2: { age: 30, country: 'USA', name: 'Дима', registred: true },
    4: { age: 20, country: 'USA', name: 'Леха', registred: false },
  },

  Usa: { 7: { age: 20, country: 'Usa', name: 'Дима', registred: false } },
};

describe('Check groupByField', () => {
  test('Check groupByField TS', () => {
    expect(groupByField(arr1, 'country')).toStrictEqual(resultMap);
  });
  test('Check groupByFieldJs JS', () => {
    expect(groupByFieldJs(arr1, 'country')).toStrictEqual(resultObject);
  });
  test('Check groupByFieldReduce JS', () => {
    expect(groupByFieldReduce(arr1, 'country')).toStrictEqual(resultMap);
  });
});
