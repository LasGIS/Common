import styled from 'styled-components';

export const MainContent = styled.div`
  max-width: 951px;
  padding: 2em 3em;
  margin: 0 auto 20px;
  background-color: #fff;
  border: 1px solid #e5e5e5;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
`;

export const Row = styled.div`
  display: flex;
  flex-direction: row;
  gap: 5px;
  //margin-left: -15px;
  //margin-right: -15px;
`;

export const InlineFormBloc = styled.div`
  position: relative;
  min-height: 1px;
  //padding-left: 15px;
  //padding-right: 15px;
  float: left;
  width: 50%;
`;

export const FormInline = styled.form``;

export const InlineFormGroup = styled.div`
  display: flex;
  flex-direction: row;
  gap: 5px;
  margin-bottom: 0;
  vertical-align: middle;
`;
