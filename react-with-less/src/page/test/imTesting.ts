import { arr1 } from './data';

const getData = () => arr1;
const res = getData();

type PropertiesType<T extends { [key: number]: unknown }> = T extends { [key: number]: infer U } ? U : never;
type FullObjectType = PropertiesType<typeof res>;
type NotAllowedKey = 'id';
type AllowedObjectType = Omit<FullObjectType, NotAllowedKey>;
type AllowedKies = Exclude<keyof FullObjectType, NotAllowedKey>;
type GroupNameFieldType = FullObjectType[AllowedKies];
type IdKeyType = FullObjectType[NotAllowedKey];

export function groupByField<T extends Array<FullObjectType>, K extends AllowedKies>(arr: T, groupBy: K) {
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
