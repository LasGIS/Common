import { RequestState } from '../../../types';

export type PersonRelationType = 'PARENT' | 'CHILD' | 'SPOUSE' | 'SIBLING' | 'RELATIVE' | 'COLLEAGUE';
export type SexType = 'MALE' | 'FEMALE';

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
  middleName: string;
  sex: SexType;
  relations: RelationType[];
};

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
