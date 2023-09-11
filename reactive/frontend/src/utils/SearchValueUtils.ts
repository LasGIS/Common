import ru from 'convert-layout/ru';

export const checkFieldForSubstringEng = (searchValue: string, value?: string): boolean =>
  !!value && (value.toLowerCase().indexOf(searchValue) !== -1 || value.toLowerCase().indexOf(ru.toEn(searchValue)) !== -1);

export const checkFieldForSubstringRuss = (searchValue: string, value?: string): boolean =>
  !!value &&
  (value.toLowerCase().indexOf(searchValue) !== -1 ||
    value.toLowerCase().indexOf(ru.toEn(searchValue)) !== -1 ||
    value.toLowerCase().indexOf(ru.fromEn(searchValue)) !== -1);

export const getSearchWords = (searchValueLowered: string): string[] => [
  searchValueLowered,
  ru.toEn(searchValueLowered),
  ru.fromEn(searchValueLowered),
];
