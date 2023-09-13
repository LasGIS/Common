import { PersonType } from './types';
import { AxiosResponse } from 'axios';
import { axiosApi } from '../../../reducer/service';

export default {
  /** Get all Persons from backend */
  requestGetAllPersons(): Promise<AxiosResponse<PersonType[]>> {
    return axiosApi.get(`/person`);
  },
  /** Get Person by personId */
  requestGetPersonById(personId: number): Promise<AxiosResponse<PersonType>> {
    return axiosApi.get(`/person/${personId}`);
  },
  /** Insert new Person to backend */
  requestCreateNewPerson(person: PersonType): Promise<AxiosResponse<PersonType>> {
    return axiosApi.post(`/person`, person);
  },
  /** Update Person in backend */
  requestUpdatePerson(person: PersonType): Promise<AxiosResponse<PersonType>> {
    return axiosApi.put(`/person/${person.personId}`, person);
  },
  /** Delete Person from backend */
  requestDeletePerson(personId: number): Promise<AxiosResponse<number>> {
    return axiosApi.delete(`/person/${personId}`);
  },
};
