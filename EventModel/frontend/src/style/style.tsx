import styled from 'styled-components';

export const MainPageWrapper = styled.div`
  width: 100vw;
  height: calc(100vh - 68px);
  background-color: #fff;
  text-align: left;
`;

export const MainContent = styled.div`
  padding: 10px;
  margin: 0 auto;
  height: calc(100vh - 88px);
  background-color: #fff;
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
  max-height: calc(100vh - 154px);
  text-align: left;
`;
