import translation from '@public/translation/ru.json';

type RecursiveKeyOf<TObj extends object> = {
  [TKey in keyof TObj & (string | number)]: TObj[TKey] extends object
    ? `${TKey}` | `${TKey}.${RecursiveKeyOf<TObj[TKey]>}`
    : `${TKey}`;
}[keyof TObj & (string | number)];

/**
 * @description Тип ключей перевода
 */
export type TranslationKeys = RecursiveKeyOf<typeof translation>;
