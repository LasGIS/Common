import React, { ReactNode } from 'react';
import { InlineFormBloc, InlineFormGroup } from '@/style/style.tsx';

interface FormProps {
  children?: ReactNode;
}

const InlineForm: React.FC<FormProps> = ({ children }) => (
  <InlineFormBloc className="col-md-6">
    <form className='form-inline'>
      <InlineFormGroup className='form-group'>
        {children}
      </InlineFormGroup>
    </form>
  </InlineFormBloc>
);

export default InlineForm;
