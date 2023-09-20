import { createAsyncThunk, createSelector, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RequestState } from '../../../types';
import { RootStoreData } from '../../../reducer/redux-types';
import { PersonStoreData, PersonType, PersonTypeJson } from './types';
import { AppDispatch } from '../../../reducer/store';
import { clearErrors } from '../../../utils/notification-utils';
import { commonHideLoader, commonShowLoader } from '../../../reducer/common';
import api from './services';
import { message } from 'antd';
import { AxiosResponse } from 'axios';
import { JsonToPersonType, JsonToPersonTypeArray } from './utils';

export const getAllPersons = createAsyncThunk<PersonTypeJson[], void, { dispatch: AppDispatch; state: PersonStoreData }>(
  'PERSON_GET_ALL',
  (vd, { dispatch, rejectWithValue }) => {
    clearErrors();
    dispatch(commonShowLoader());
    return api
      .requestGetAllPersons()
      .then((response: AxiosResponse<PersonTypeJson[]>) => {
        return JsonToPersonTypeArray(response.data);
      })
      .catch((error) => {
        return rejectWithValue(error);
      })
      .finally(() => {
        dispatch(commonHideLoader());
      });
  },
);

export const getPersonById = createAsyncThunk<PersonType, number, { dispatch: AppDispatch; state: PersonStoreData }>(
  'PERSON_GET_BY_ID',
  (personId, { dispatch, rejectWithValue }) => {
    clearErrors();
    dispatch(commonShowLoader());
    return api
      .requestGetPersonById(personId)
      .then((response: AxiosResponse<PersonTypeJson>) => {
        return JsonToPersonType(response.data);
      })
      .catch((error) => {
        return rejectWithValue(error);
      })
      .finally(() => {
        dispatch(commonHideLoader());
      });
  },
);

export const insertNewPerson = createAsyncThunk<
  PersonType,
  { person: PersonType; onSuccess: () => void },
  { dispatch: AppDispatch; state: PersonStoreData }
>('PERSON_CREATE', ({ person, onSuccess }, { dispatch, rejectWithValue }) => {
  clearErrors();
  dispatch(commonShowLoader());
  return api
    .requestCreateNewPerson(person)
    .then((response: AxiosResponse<PersonTypeJson>) => {
      dispatch(getAllPersons() as AppDispatch);
      onSuccess();
      return JsonToPersonType(response.data);
    })
    .catch((error) => {
      return rejectWithValue({ error, person: person });
    })
    .finally(() => {
      dispatch(commonHideLoader());
    });
});

export const updatePerson = createAsyncThunk<
  PersonType,
  { person: PersonType; onSuccess: () => void },
  { dispatch: AppDispatch; state: PersonStoreData }
>('PERSON_UPDATE', ({ person, onSuccess }, { dispatch, rejectWithValue }) => {
  clearErrors();
  dispatch(commonShowLoader());
  return api
    .requestUpdatePerson(person)
    .then((response: AxiosResponse<PersonTypeJson>) => {
      dispatch(getAllPersons() as AppDispatch);
      onSuccess();
      return JsonToPersonType(response.data);
    })
    .catch((error) => {
      return rejectWithValue({ error, person: person });
    })
    .finally(() => {
      dispatch(commonHideLoader());
    });
});

export const deletePersonById = createAsyncThunk<number, number, { dispatch: AppDispatch; state: PersonStoreData }>(
  'PERSON_DELETE_PERSON',
  (personId, { dispatch, rejectWithValue }) => {
    clearErrors();
    dispatch(commonShowLoader());
    return api
      .requestDeletePerson(personId)
      .then(() => {
        dispatch(getAllPersons() as AppDispatch);
        return personId;
      })
      .catch((error) => {
        return rejectWithValue(error);
      })
      .finally(() => {
        dispatch(commonHideLoader());
      });
  },
);

const PersonManagementSlice = createSlice({
  name: 'PERSON',
  initialState: {
    searchValue: '',
    allPersons: [],
    currentPerson: undefined,
    isCurrentPersonShow: false,
    isNewPerson: false,

    personsRequestState: RequestState.UNDEFINED,
    getPersonRequestState: RequestState.UNDEFINED,
    insertPersonRequestState: RequestState.UNDEFINED,
    updatePersonRequestState: RequestState.UNDEFINED,
    deletePersonRequestState: RequestState.UNDEFINED,
  },
  reducers: {
    setCurrentPerson(state: PersonStoreData, action: PayloadAction<PersonType>) {
      state.currentPerson = action.payload;
    },
    currentPersonShow(state: PersonStoreData, action: PayloadAction<boolean>) {
      state.isCurrentPersonShow = action.payload;
    },
    setIsNewPerson(state: PersonStoreData, action: PayloadAction<boolean>) {
      state.isNewPerson = action.payload;
    },
    setPersonSearchValue(state: PersonStoreData, action: PayloadAction<string>) {
      state.searchValue = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder

      /* getAllPersons (PERSON_GET_ALL) */
      .addCase(getAllPersons.pending.type, (state: PersonStoreData) => {
        state.personsRequestState = RequestState.START;
      })
      .addCase(getAllPersons.fulfilled.type, (state: PersonStoreData, action: PayloadAction<PersonType[]>) => {
        state.allPersons = action.payload;
        state.personsRequestState = RequestState.SUCCESS;
      })
      .addCase(getAllPersons.rejected.type, (state: PersonStoreData) => {
        state.personsRequestState = RequestState.FAIL;
      })

      /* getPersonById (PERSON_GET_BY_ID) */
      .addCase(getPersonById.pending.type, (state: PersonStoreData) => {
        state.isNewPerson = false;
        state.getPersonRequestState = RequestState.START;
      })
      .addCase(getPersonById.fulfilled.type, (state: PersonStoreData, action: PayloadAction<PersonType>) => {
        state.currentPerson = action.payload;
        state.isCurrentPersonShow = true;
        state.getPersonRequestState = RequestState.SUCCESS;
      })
      .addCase(getPersonById.rejected.type, (state: PersonStoreData) => {
        state.getPersonRequestState = RequestState.FAIL;
      })

      /* insertPerson (PERSON_CREATE) */
      .addCase(insertNewPerson.pending.type, (state: PersonStoreData) => {
        state.isCurrentPersonShow = false;
        state.insertPersonRequestState = RequestState.START;
      })
      .addCase(insertNewPerson.fulfilled.type, (state: PersonStoreData, action: PayloadAction<PersonType>) => {
        state.currentPerson = action.payload;
        state.insertPersonRequestState = RequestState.SUCCESS;
        message.info(`Person "${action.payload.firstName} ${action.payload.lastName}" успешно ДОБАВЛЕН`);
      })
      .addCase(insertNewPerson.rejected.type, (state: PersonStoreData, action: PayloadAction<{ error: unknown; Person: PersonType }>) => {
        state.isCurrentPersonShow = true;
        state.currentPerson = action.payload.Person;
        state.insertPersonRequestState = RequestState.FAIL;
      })

      /* updatePerson (PERSON_UPDATE) */
      .addCase(updatePerson.pending.type, (state: PersonStoreData) => {
        state.isCurrentPersonShow = false;
        state.updatePersonRequestState = RequestState.START;
      })
      .addCase(updatePerson.fulfilled.type, (state: PersonStoreData, action: PayloadAction<PersonType>) => {
        state.currentPerson = action.payload;
        state.isCurrentPersonShow = true;
        state.updatePersonRequestState = RequestState.SUCCESS;
        message.info(`Person "${action.payload.firstName} ${action.payload.lastName}" успешно ОБНОВЛЕН`);
      })
      .addCase(updatePerson.rejected.type, (state: PersonStoreData, action: PayloadAction<{ error: unknown; Person: PersonType }>) => {
        state.isCurrentPersonShow = true;
        state.currentPerson = action.payload.Person;
        state.updatePersonRequestState = RequestState.FAIL;
      })

      /* deletePersonByIp (PERSON_DELETE_PERSON) */
      .addCase(deletePersonById.pending.type, (state: PersonStoreData) => {
        state.isNewPerson = false;
        state.deletePersonRequestState = RequestState.START;
      })
      .addCase(deletePersonById.fulfilled.type, (state: PersonStoreData, action: PayloadAction<number>) => {
        state.currentPerson = undefined;
        state.isCurrentPersonShow = false;
        state.deletePersonRequestState = RequestState.SUCCESS;
        message.info(`Person "${action.payload}" успешно УДАЛЕН`);
      })
      .addCase(deletePersonById.rejected.type, (state: PersonStoreData) => {
        state.deletePersonRequestState = RequestState.FAIL;
      });
  },
});

export const PersonManagementRootSelector = (state: RootStoreData) => state.person;

export const selectSearchValue = createSelector(PersonManagementRootSelector, (state) => state && state.searchValue);
export const selectAllPersons = createSelector(PersonManagementRootSelector, (state) => state && state.allPersons);
export const selectCurrentPerson = createSelector(PersonManagementRootSelector, (state) => state && state.currentPerson);
export const isCurrentPersonShowSelector = createSelector(PersonManagementRootSelector, (state) =>
  Boolean(state && state.isCurrentPersonShow && (state.getPersonRequestState === RequestState.SUCCESS || state.isNewPerson) && state.currentPerson),
);
export const selectIsNewPerson = createSelector(PersonManagementRootSelector, (state) => state && state.isNewPerson);
export const selectPersonsRequestState = createSelector(PersonManagementRootSelector, (state) => state && state.personsRequestState);

export const { setCurrentPerson, currentPersonShow, setIsNewPerson, setPersonSearchValue } = PersonManagementSlice.actions;

export const createPerson = (person: PersonType) => (dispatch: AppDispatch) => {
  clearErrors();
  dispatch(setCurrentPerson(person));
  dispatch(setIsNewPerson(true));
  dispatch(currentPersonShow(true));
};

export default PersonManagementSlice.reducer;
