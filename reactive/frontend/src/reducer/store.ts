import { configureStore } from '@reduxjs/toolkit';
import { useDispatch } from 'react-redux';
import CommonReducer from './common';
import UserReducer from '../page/User/reducer';
import PersonReducer from '../page/Person/reducer';

const store = configureStore({
  reducer: {
    common: CommonReducer,
    user: UserReducer,
    person: PersonReducer,
  },
});

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;
export const useAppDispatch: () => AppDispatch = useDispatch;

export default store;
