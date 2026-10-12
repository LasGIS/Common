enum AlgorithmType {
  DARK = 'darkAlgorithm',
  DEFAULT = 'defaultAlgorithm',
}

enum LangType {
  RU = 'ru',
  EN = 'en',
}

const SUPPORTED_LANGUAGES = [LangType.RU, LangType.EN];

interface SettingsState {
  lang?: LangType;
  algorithm: AlgorithmType;
}

export type { SettingsState };

export { AlgorithmType, LangType, SUPPORTED_LANGUAGES };
