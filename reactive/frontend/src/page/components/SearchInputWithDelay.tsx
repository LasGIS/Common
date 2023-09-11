import React, { ChangeEvent, CSSProperties, useEffect, useState } from 'react';
import Search from 'antd/es/input/Search';

type Props = {
  style?: CSSProperties;
  value: string;
  placeholder: string;
  onChange: (value: string, mandatory: boolean) => void;
  delay?: number;
};

const SearchInputWithDelay = ({ style, value, placeholder, onChange, delay = 500 }: Props) => {
  const [timer, setTimer] = useState<NodeJS.Timeout | undefined>(undefined);
  const [tempValue, setTempValue] = useState<string>(value);

  useEffect(() => {
    setTempValue(value);
  }, [value]);

  const onPressEnter = () => {
    if (timer) clearTimeout(timer);
    setTimer(undefined);
    onChange(tempValue, true);
  };

  const onChangeValue = (e: ChangeEvent<HTMLInputElement>) => {
    const newValue = e.target.value;
    setTempValue(newValue);
    if (timer) clearTimeout(timer);
    setTimer(
      setTimeout(() => {
        onChange(newValue, false);
      }, delay),
    );
  };

  return (
    <Search
      style={style}
      value={tempValue}
      onChange={onChangeValue}
      onPressEnter={onPressEnter}
      onSearch={(value) => onChange(value, true)}
      placeholder={placeholder}
      allowClear
    />
  );
};

export default SearchInputWithDelay;
