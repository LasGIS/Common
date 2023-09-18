import { PersonType, SexTypeMap } from './types';

export const personToFio = (person: PersonType): string => `${person.firstName} ${person.middleName} ${person.lastName}`;

export const personToSex = (person: PersonType): string => SexTypeMap[person.sex];
