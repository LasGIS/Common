import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { GeoObject, ObjectsState } from '@/types/redux/ObjectsTypes.ts';

const initialState: ObjectsState = {
  lastId: 1,
  objects: {},
};

export const objectsSlice = createSlice({
  name: 'objects',
  initialState,
  reducers: {
    addGeoObject: (state, { payload }: PayloadAction<GeoObject>) => {
      payload.id = state.lastId++;
      state.objects[payload.id] = payload;
    },
    removeGeoObject: (state, { payload }: PayloadAction<number>) => {
      delete state.objects[payload];
    },
  },
  selectors: {
    objectsSelector: (state): Record<number, GeoObject> => state.objects,
  },
});

export const { addGeoObject, removeGeoObject } = objectsSlice.actions;
export const { objectsSelector } = objectsSlice.selectors;

export default objectsSlice.reducer;
