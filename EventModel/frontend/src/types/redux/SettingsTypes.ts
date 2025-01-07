interface SettingsState {
  lang?: LangType;
}

enum LangType {
  RU = 'ru',
  ENG = 'eng',
}

const SUPPORTED_LANGUAGES = [LangType.RU, LangType.ENG];

export { SettingsState, LangType, SUPPORTED_LANGUAGES };
