export const groupByFieldJs = (arr, groupBy) => {
  let result = {};
  arr.forEach((item) => {
    const { id, ...obj } = item;
    const groupName = item[groupBy];
    if (!result[groupName]) {
      result[groupName] = {};
    }
    result[groupName][id] = obj;
  });
  console.log(result);
  return result;
};

export const groupByFieldReduce = (arr, groupBy) => {
  const result = arr.reduce((acc, item) => {
    const { id, ...obj } = item;
    const groupName = item[groupBy];
    if (acc.has(groupName)) {
      acc.get(groupName).set(id, obj);
    } else {
      acc.set(groupName, new Map().set(id, obj));
    }
    return acc;
  }, new Map());
  console.log(result);
  return result;
};
