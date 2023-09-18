import { RequestState } from '../../../types';

export type PersonRelationType = 'PARENT' | 'CHILD' | 'SPOUSE' | 'SIBLING' | 'RELATIVE' | 'COLLEAGUE';
export type SexType = 'MALE' | 'FEMALE';

export const PersonRelationTypeMap: Record<PersonRelationType, { name: string; MALE: string; FEMALE: string }> = {
  PARENT: { name: 'родитель (мать или отец)', MALE: 'отец', FEMALE: 'мать' },
  CHILD: { name: 'ребёнок (сын или дочь)', MALE: 'сын', FEMALE: 'дочь' },
  SPOUSE: { name: 'супруг (муж или жена)', MALE: 'муж', FEMALE: 'жена' },
  SIBLING: { name: 'родной брат или сестра', MALE: 'брат', FEMALE: 'сестра' },
  RELATIVE: { name: 'родственник или родственница', MALE: 'родственник', FEMALE: 'родственница' },
  COLLEAGUE: { name: 'коллега по работе', MALE: 'сотрудник', FEMALE: 'сотрудница' },
};

export const SexTypeOption: { code: SexType; name: string }[] = [
  { code: 'MALE', name: 'Мужчина' },
  { code: 'FEMALE', name: 'Женщина' },
];

export const SexTypeMap: Record<SexType, string> = SexTypeOption.reduce((acc, option) => {
  acc[option.code] = option.name;
  return acc;
}, {} as Record<SexType, string>);

export type RelationType = {
  personId: number;
  personToId: number;
  personTo: PersonType;
  type: PersonRelationType;
};

export type PersonType = {
  personId: number;
  firstName: string;
  lastName: string;
  middleName?: string;
  sex?: SexType;
  relations: RelationType[];
};

export class NewPerson implements PersonType {
  personId: number;
  firstName: string;
  lastName: string;
  relations: [];

  constructor() {
    this.personId = 0;
    this.firstName = '';
    this.lastName = '';
    this.relations = [];
  }
}

export interface PersonStoreData {
  searchValue: string;
  allPersons: PersonType[];
  currentPerson?: PersonType;
  isCurrentPersonShow: boolean;
  isNewPerson: boolean;

  personsRequestState: RequestState;
  getPersonRequestState: RequestState;
  insertPersonRequestState: RequestState;
  updatePersonRequestState: RequestState;
  deletePersonRequestState: RequestState;
}
