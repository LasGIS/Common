import { GeoObject } from '@/types/redux/ObjectsTypes.ts';
import { addGeoObject } from '@/redux/reducer/ObjectsReducer.ts';
import { useAppDispatch } from '@/redux';

export const useAddGeoObject = () => {
  const dispatch = useAppDispatch();

  return (geo: GeoObject) => {
    dispatch(addGeoObject(geo));
  };
};
