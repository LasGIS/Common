export function test() {
  const arr1 = [
    { id: 1, age: 20, name: 'Иван', country: 'Russia', registred: true },
    { id: 2, age: 30, name: 'Дима', country: 'USA', registred: true },
    { id: 3, age: 25, name: 'Леха', country: 'Russia', registred: false },
    { id: 4, age: 20, name: 'Леха', country: 'USA', registred: false },
    { id: 5, age: 30, name: 'Иван', country: 'Russia', registred: true },
    { id: 6, age: 50, name: 'Леха', country: 'Russia', registred: true },
    { id: 7, age: 20, name: 'Дима', country: 'Usa', registred: false }
  ];
  const arr2 = [
    { id: 1, type: 3, modelName: 'Audi' },
    { id: 2, type: 4, modelName: 'Ford' }
  ];
  const arr3 = [
    { id: 1, type: '1', modelName: 'Audi' },
    { id: 2, type: '3', modelName: 'Ford' }
  ];
  const getData = () => arr1;
  const res = getData();

  type PropertiesType<T extends { [key: number]: unknown }> = T extends { [key: number]: infer U } ? U : never;
  type FullObjectType = PropertiesType<typeof res>;
  type NotAllowedKey = 'id';
  type AllowedObjectType = Omit<FullObjectType, NotAllowedKey>;
  type AllowedKies = Exclude<keyof FullObjectType, NotAllowedKey>;
  type GroupNameFieldType = FullObjectType[AllowedKies];
  type IdKeyType = FullObjectType[NotAllowedKey];

  function groupByField<T extends Array<FullObjectType>, K extends AllowedKies>(arr: T, groupBy: K) {
    let result = new Map<GroupNameFieldType, Map<IdKeyType, AllowedObjectType>>();
    arr.forEach((item) => {
      const groupName = item[groupBy];
      const { id, ...rest } = item;
      if (!result.has(groupName)) {
        result.set(groupName, new Map<IdKeyType, AllowedObjectType>().set(id, rest));
      } else {
        result.get(groupName)?.set(id, rest);
      }
    });
    console.log(result);
    return result;
  }

  groupByField(res, 'country');
}
