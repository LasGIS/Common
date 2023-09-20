import { PersonRelationType, PersonRelationTypeMap, PersonType, PersonTypeJson, SexType, SexTypeMap } from './types';

const personToFio = (person: PersonType): string => {
  let fio: string = person.firstName ? `${person.firstName.charAt(0).toUpperCase()}.` : '';
  if (person.middleName) {
    fio += `${person.middleName.charAt(0).toUpperCase()}.`;
  }
  fio += person.lastName;
  return fio;
};

export const personToSex = (person: PersonType): string => (person.sex ? SexTypeMap[person.sex] : '');

export const personRelationType = (relation: PersonRelationType, personSex?: SexType): string => {
  const record: { name: string; MALE: string; FEMALE: string } = PersonRelationTypeMap[relation];
  return personSex ? record[personSex] : record.name;
};

export const JsonToPersonType = (person: PersonTypeJson): PersonType => {
  const fio = personToFio(person as PersonType);
  const relations = person.relations?.map((rel) => {
    const personTo: PersonType = rel.personTo ? JsonToPersonType(rel.personTo) : rel.personTo;
    return { ...rel, personTo };
  });
  return { ...person, fio, relations };
};

export const JsonToPersonTypeArray = (array: PersonTypeJson[]): PersonType[] => {
  return array.map((json) => JsonToPersonType(json));
};
