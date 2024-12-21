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
  gap: 10px;
`;

export const FormBloc = styled.div`
  position: relative;
  min-height: 1px;
  float: left;
  width: 50%;
`;

export const FormInline = styled.form``;

export const FormGroup = styled.div`
  display: flex;
  flex-direction: row;
  gap: 10px;
  margin-bottom: 0;
  vertical-align: middle;
`;

export const ListBloc = styled.div`
  border: 1px solid #e5e5e5;
  margin-top: 10px;
  padding: 10px;
  overflow: auto;
  max-height: calc(100vh - 200px);
  text-align: left;
`;
