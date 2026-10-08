export const sum = (v1: number, v2: number) => v1 + v2;

export const divide = (v1: number, v2: number) => {
  if (v2 === 0) {
    throw new Error('Деление на ноль невозможно');
  }
  return v1 / v2;
};
