import { createAsyncThunk, createSelector, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RequestState } from '../../../types';
import { RootStoreData } from '../../../reducer/redux-types';
import { UserStoreData, UserType } from './types';
import { AppDispatch } from '../../../reducer/store';
import { clearErrors } from '../../../utils/notification-utils';
import { commonHideLoader, commonShowLoader } from '../../../reducer/common';
import api from './services';
import { message } from 'antd';
import { AxiosResponse } from 'axios';

export const getAllUsers = createAsyncThunk<UserType[], void, { dispatch: AppDispatch; state: UserStoreData }>(
  'USER_GET_ALL',
  (vd, { dispatch, rejectWithValue }) => {
    clearErrors();
    dispatch(commonShowLoader());
    return api
      .requestGetAllUsers()
      .then((response: AxiosResponse<UserType[]>) => {
        return response.data;
      })
      .catch((error) => {
        return rejectWithValue(error);
      })
      .finally(() => {
        dispatch(commonHideLoader());
      });
  },
);

export const getUserById = createAsyncThunk<UserType, number, { dispatch: AppDispatch; state: UserStoreData }>(
  'USER_GET_BY_ID',
  (userId, { dispatch, rejectWithValue }) => {
    clearErrors();
    dispatch(commonShowLoader());
    return api
      .requestGetUserById(userId)
      .then((response: AxiosResponse<UserType>) => {
        return response.data;
      })
      .catch((error) => {
        return rejectWithValue(error);
      })
      .finally(() => {
        dispatch(commonHideLoader());
      });
  },
);

/*
export const getUserByLogin = createAsyncThunk<
  UserType,
  { login: string; onSuccess: (login?: string) => void; onFail: () => void },
  { dispatch: AppDispatch; state: UserStoreData }
>('USER_GET_BY_LOGIN', ({ login, onSuccess, onFail }, { dispatch, rejectWithValue }) => {
  clearErrors();
  dispatch(commonShowLoader());
  return requestGetUserByLogin(login)
    .then((response: AxiosResponse<UserType>) => {
      const user: UserType = response.data;
      onSuccess(user.login);
      return user;
    })
    .catch((error) => {
      onFail();
      return rejectWithValue(error);
    })
    .finally(() => {
      dispatch(commonHideLoader());
    });
});
*/

export const insertUser = createAsyncThunk<UserType, { user: UserType; onSuccess: () => void }, { dispatch: AppDispatch; state: UserStoreData }>(
  'USER_CREATE',
  ({ user, onSuccess }, { dispatch, rejectWithValue }) => {
    clearErrors();
    dispatch(commonShowLoader());
    return api
      .requestCreateNewUser(user)
      .then((response: AxiosResponse<UserType>) => {
        dispatch(getAllUsers() as AppDispatch);
        onSuccess();
        return response.data;
      })
      .catch((error) => {
        return rejectWithValue({ error, user });
      })
      .finally(() => {
        dispatch(commonHideLoader());
      });
  },
);

export const updateUser = createAsyncThunk<UserType, { user: UserType; onSuccess: () => void }, { dispatch: AppDispatch; state: UserStoreData }>(
  'USER_UPDATE',
  ({ user, onSuccess }, { dispatch, rejectWithValue }) => {
    clearErrors();
    dispatch(commonShowLoader());
    return api
      .requestUpdateUser(user)
      .then((response: AxiosResponse<UserType>) => {
        dispatch(getAllUsers() as AppDispatch);
        onSuccess();
        return response.data;
      })
      .catch((error) => {
        return rejectWithValue({ error, user });
      })
      .finally(() => {
        dispatch(commonHideLoader());
      });
  },
);

export const deleteUserById = createAsyncThunk<number, number, { dispatch: AppDispatch; state: UserStoreData }>(
  'USER_DELETE_USER',
  (userId, { dispatch, rejectWithValue }) => {
    clearErrors();
    dispatch(commonShowLoader());
    return api
      .requestDeleteUser(userId)
      .then(() => {
        dispatch(getAllUsers() as AppDispatch);
        return userId;
      })
      .catch((error) => {
        return rejectWithValue(error);
      })
      .finally(() => {
        dispatch(commonHideLoader());
      });
  },
);

const userManagementSlice = createSlice({
  name: 'USER',
  initialState: {
    searchValue: '',
    allUsers: [],
    currentUser: undefined,
    isCurrentUserShow: false,
    isNewUser: false,

    usersRequestState: RequestState.UNDEFINED,
    getUserRequestState: RequestState.UNDEFINED,
    insertUserRequestState: RequestState.UNDEFINED,
    updateUserRequestState: RequestState.UNDEFINED,
    deleteUserRequestState: RequestState.UNDEFINED,
  },
  reducers: {
    setCurrentUser(state: UserStoreData, action: PayloadAction<UserType>) {
      state.currentUser = action.payload;
    },
    currentUserShow(state: UserStoreData, action: PayloadAction<boolean>) {
      state.isCurrentUserShow = action.payload;
    },
    setIsNewUser(state: UserStoreData, action: PayloadAction<boolean>) {
      state.isNewUser = action.payload;
    },
    setUserSearchValue(state: UserStoreData, action: PayloadAction<string>) {
      state.searchValue = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder

      /* getAllUsers (USER_GET_ALL) */
      .addCase(getAllUsers.pending.type, (state: UserStoreData) => {
        state.usersRequestState = RequestState.START;
      })
      .addCase(getAllUsers.fulfilled.type, (state: UserStoreData, action: PayloadAction<UserType[]>) => {
        state.allUsers = action.payload;
        state.usersRequestState = RequestState.SUCCESS;
      })
      .addCase(getAllUsers.rejected.type, (state: UserStoreData) => {
        state.usersRequestState = RequestState.FAIL;
      })

      /* getUserById (USER_GET_BY_ID) */
      .addCase(getUserById.pending.type, (state: UserStoreData) => {
        state.isNewUser = false;
        state.getUserRequestState = RequestState.START;
      })
      .addCase(getUserById.fulfilled.type, (state: UserStoreData, action: PayloadAction<UserType>) => {
        state.currentUser = action.payload;
        state.isCurrentUserShow = true;
        state.getUserRequestState = RequestState.SUCCESS;
      })
      .addCase(getUserById.rejected.type, (state: UserStoreData) => {
        state.getUserRequestState = RequestState.FAIL;
      })

      /* insertUser (USER_CREATE) */
      .addCase(insertUser.pending.type, (state: UserStoreData) => {
        state.isCurrentUserShow = false;
        state.insertUserRequestState = RequestState.START;
      })
      .addCase(insertUser.fulfilled.type, (state: UserStoreData, action: PayloadAction<UserType>) => {
        state.currentUser = action.payload;
        state.insertUserRequestState = RequestState.SUCCESS;
        message.info(`USER "${action.payload.login}" успешно ДОБАВЛЕН`);
      })
      .addCase(insertUser.rejected.type, (state: UserStoreData, action: PayloadAction<{ error: unknown; user: UserType }>) => {
        state.isCurrentUserShow = true;
        state.currentUser = action.payload.user;
        state.insertUserRequestState = RequestState.FAIL;
      })

      /* updateUser (USER_UPDATE) */
      .addCase(updateUser.pending.type, (state: UserStoreData) => {
        state.isCurrentUserShow = false;
        state.updateUserRequestState = RequestState.START;
      })
      .addCase(updateUser.fulfilled.type, (state: UserStoreData, action: PayloadAction<UserType>) => {
        state.currentUser = action.payload;
        state.isCurrentUserShow = true;
        state.updateUserRequestState = RequestState.SUCCESS;
        message.info(`USER "${action.payload.login}" успешно ОБНОВЛЕН`);
      })
      .addCase(updateUser.rejected.type, (state: UserStoreData, action: PayloadAction<{ error: unknown; user: UserType }>) => {
        state.isCurrentUserShow = true;
        state.currentUser = action.payload.user;
        state.updateUserRequestState = RequestState.FAIL;
      })

      /* deleteUserByIp (USER_DELETE_USER) */
      .addCase(deleteUserById.pending.type, (state: UserStoreData) => {
        state.isNewUser = false;
        state.deleteUserRequestState = RequestState.START;
      })
      .addCase(deleteUserById.fulfilled.type, (state: UserStoreData, action: PayloadAction<number>) => {
        state.currentUser = undefined;
        state.isCurrentUserShow = false;
        state.deleteUserRequestState = RequestState.SUCCESS;
        message.info(`USER "${action.payload}" успешно УДАЛЕН`);
      })
      .addCase(deleteUserById.rejected.type, (state: UserStoreData) => {
        state.deleteUserRequestState = RequestState.FAIL;
      });
  },
});

export const userManagementRootSelector = (state: RootStoreData) => state.user;

export const selectSearchValue = createSelector(userManagementRootSelector, (state) => state && state.searchValue);
export const selectAllUsers = createSelector(userManagementRootSelector, (state) => state && state.allUsers);
export const selectCurrentUser = createSelector(userManagementRootSelector, (state) => state && state.currentUser);
export const isCurrentUserShowSelector = createSelector(userManagementRootSelector, (state) =>
  Boolean(state && state.isCurrentUserShow && (state.getUserRequestState === RequestState.SUCCESS || state.isNewUser) && state.currentUser),
);
export const selectIsNewUser = createSelector(userManagementRootSelector, (state) => state && state.isNewUser);
export const selectUsersRequestState = createSelector(userManagementRootSelector, (state) => state && state.usersRequestState);

export const { setCurrentUser, currentUserShow, setIsNewUser, setUserSearchValue } = userManagementSlice.actions;

export const createUser = (user: UserType) => (dispatch: AppDispatch) => {
  clearErrors();
  dispatch(setCurrentUser)(user);
  dispatch(setIsNewUser)(true);
  dispatch(currentUserShow)(true);
};

export default userManagementSlice.reducer;
