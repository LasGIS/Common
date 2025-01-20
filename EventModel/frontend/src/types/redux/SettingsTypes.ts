interface SettingsState {
  lang?: LangType;
}

enum LangType {
  RU = 'ru',
  EN = 'en',
}

const SUPPORTED_LANGUAGES = [LangType.RU, LangType.EN];

export { SettingsState, LangType, SUPPORTED_LANGUAGES };
