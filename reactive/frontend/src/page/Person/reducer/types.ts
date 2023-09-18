import { RequestState } from '../../../types';

export type PersonRelationType = 'PARENT' | 'CHILD' | 'SPOUSE' | 'SIBLING' | 'RELATIVE' | 'COLLEAGUE';
export type SexType = 'UNDEF' | 'MALE' | 'FEMALE';

export const PersonRelationTypeOption = [
  { code: 'UNDEF', name: ' ' },
  { code: 'PARENT', name: 'родитель (мать или отец)' },
  { code: 'CHILD', name: 'ребёнок (сын или дочь)' },
  { code: 'SPOUSE', name: 'супруг (муж или жена)' },
  { code: 'SIBLING', name: 'родной брат или сестра' },
  { code: 'RELATIVE', name: 'родственник, родственница' },
  { code: 'COLLEAGUE', name: 'коллега по работе' },
];

export const SexTypeOption: { code: SexType; name: string }[] = [
  { code: 'UNDEF', name: ' ' },
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
  sex: SexType;
  relations: RelationType[];
};

export class NewPerson implements PersonType {
  personId: number;
  firstName: string;
  lastName: string;
  sex: SexType;
  relations: [];

  constructor() {
    this.personId = 0;
    this.firstName = '';
    this.lastName = '';
    this.sex = 'UNDEF';
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
