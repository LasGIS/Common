import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
import { configureStore } from '@reduxjs/toolkit';
import settingsReducer from '@/redux/reducer/SettingsReducer.ts';
import objectsReducer from '@/redux/reducer/ObjectsReducer.ts';

const store = configureStore({
  reducer: {
    settings: settingsReducer,
    objects: objectsReducer,
  },
});

export type AppStore = typeof store;
export type AppDispatch = AppStore['dispatch'];
export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
export type RootState = ReturnType<typeof store.getState>;

export default store;
