import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { AlgorithmType, LangType, SettingsState } from '@/types/redux/SettingsTypes.ts';

const initialState: SettingsState = {
  lang: (localStorage.getItem('language') as LangType) || LangType.RU,
  algorithm: AlgorithmType.DEFAULT,
};

export const settingsSlice = createSlice({
  name: 'settings',
  initialState,
  reducers: {
    setLang: (state, action: PayloadAction<LangType>) => {
      state.lang = action.payload;
    },
    setThemeAlgorithm: (state, action: PayloadAction<AlgorithmType>) => {
      state.algorithm = action.payload;
    },
  },
  selectors: {
    algorithmSelector: (state) => state.algorithm,
    langSelector: (state) => state.lang || LangType.RU,
  },
});

export const { setLang, setThemeAlgorithm } = settingsSlice.actions;
export const { algorithmSelector, langSelector } = settingsSlice.selectors;

export default settingsSlice.reducer;
