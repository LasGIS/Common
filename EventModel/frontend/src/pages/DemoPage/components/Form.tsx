import React, { ReactNode } from 'react';
import { FormBloc, FormGroup } from '@/style/style.tsx';

interface FormProps {
  children?: ReactNode;
}

const Form: React.FC<FormProps> = ({ children }) => (
  <FormBloc>
    <FormGroup>{children}</FormGroup>
  </FormBloc>
);

export default Form;
