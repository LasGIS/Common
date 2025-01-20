import React from 'react';
import { Tooltip, TooltipProps } from 'antd';

export const StyledTooltip = ({ children, ...rest }: TooltipProps) => {
  if (!rest.title) return children;

  return (
    <div style={{ display: 'contents' }}>
      <Tooltip
        overlayInnerStyle={{ fontSize: 10, minHeight: 'auto', color: '#B8D5EE' }}
        style={{ padding: '4px' }}
        color={'#1C4682'}
        arrow={false}
        placement={'bottomLeft'}
        {...rest}
      >
        {children}
      </Tooltip>
    </div>
  );
};
