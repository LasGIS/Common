import { PersonRelationType, PersonRelationTypeMap, PersonType, SexType, SexTypeMap } from './types';

export const personToFio = (person: PersonType): string => `${person.firstName} ${person.middleName} ${person.lastName}`;

export const personToSex = (person: PersonType): string => (person.sex ? SexTypeMap[person.sex] : '');

export const personRelationType = (relation: PersonRelationType, personSex?: SexType): string => {
  const record: { name: string; MALE: string; FEMALE: string } = PersonRelationTypeMap[relation];
  return personSex ? record[personSex] : record.name;
};
