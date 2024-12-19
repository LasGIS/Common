import React, { ReactNode } from 'react';
import { InlineFormBloc, InlineFormGroup } from '@/style/style.tsx';

interface FormProps {
  children?: ReactNode;
}

const InlineForm: React.FC<FormProps> = ({ children }) => (
  <InlineFormBloc>
    <InlineFormGroup>{children}</InlineFormGroup>
  </InlineFormBloc>
);

export default InlineForm;
