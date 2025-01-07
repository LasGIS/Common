import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { LangType, SettingsState } from '@/types/redux/SettingsTypes.ts';

const initialState: SettingsState = {
  lang: (localStorage.getItem('language') as LangType) || LangType.RU,
};

export const settingsSlice = createSlice({
  name: 'settings',
  initialState,
  reducers: {
    setLang: (state, action: PayloadAction<LangType>) => {
      state.lang = action.payload;
    },
  },
  selectors: {
    langSelector: (state) => state.lang || LangType.RU,
  },
});

export const { setLang } = settingsSlice.actions;
export const { langSelector } = settingsSlice.selectors;

export default settingsSlice.reducer;
