const arr1 = [
  { id: 1, age: 20, name: 'Иван', country: 'Russia', registred: true },
  { id: 2, age: 30, name: 'Дима', country: 'USA', registred: true },
  { id: 3, age: 25, name: 'Леха', country: 'Russia', registred: false },
  { id: 4, age: 20, name: 'Леха', country: 'USA', registred: false },
  { id: 5, age: 30, name: 'Иван', country: 'Russia', registred: true },
  { id: 6, age: 50, name: 'Леха', country: 'Russia', registred: true },
  { id: 7, age: 20, name: 'Дима', country: 'Usa', registred: false },
];
const arr2 = [
  { id: 1, type: 3, modelName: 'Audi' },
  { id: 2, type: 4, modelName: 'Ford' },
];
const arr3 = [
  { id: 1, type: '1', modelName: 'Audi' },
  { id: 2, type: '3', modelName: 'Ford' },
];
const getData = () => arr1;
const res = getData();

const groupByField = (arr, groupBy) => {
  let result = {};
  arr.forEach((item) => {
    const { id } = item;
    const groupName = item[groupBy];
    if (!result[groupBy]) {
      result[groupBy] = {};
    }
    if (!result[groupBy][groupName]) {
      result[groupBy][groupName] = {};
    }
    result[groupBy][groupName][id] = item;
  });
  console.log(result);
  return result;
};

const groupByFieldReduce = (arr, groupBy) => {
  const result = arr.reduce((acc, item) => {
    const { id } = item;
    const groupName = item[groupBy];
    let groupByObj = acc[groupBy];
    if (!groupByObj) groupByObj = acc[groupBy] = {};
    let groupNameObj = groupByObj[groupName];
    if (!groupNameObj) groupNameObj = groupByObj[groupName] = {};
    groupNameObj[id] = item;
    return acc;
  }, {});
  console.log(result);
  return result;
};

groupByFieldReduce(res, 'name');
