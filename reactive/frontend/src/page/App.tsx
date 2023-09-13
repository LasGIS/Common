import { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CommonLayout from './Main/CommonLayout';
import LoginPage from './Login/LoginPage';
import { AppDispatch, useAppDispatch } from '../reducer/store';
import { getAppSettings } from '../reducer/common';
import UsersPage from './User/UsersPage';
import UserDetailForm from './User/UserDetailForm';
import PersonPage from './Person/PersonPage';
import PersonDetailForm from './Person/PersonDetailForm';

const App = () => {
  const dispatch: AppDispatch = useAppDispatch();
  useEffect(() => {
    dispatch(getAppSettings() as AppDispatch);
  }, [dispatch]);

  return (
    <Router basename={process.env.PUBLIC_URL}>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<CommonLayout />}>
          <Route index element={<div>Main</div>} />
          <Route path="/main" element={<div>Main</div>} />
          <Route path="/user" element={<UsersPage />} />
          <Route path="/user/:userId" element={<UserDetailForm />} />
          <Route path="/person" element={<PersonPage />} />
          <Route path="/person/:personId" element={<PersonDetailForm />} />
        </Route>
      </Routes>
    </Router>
  );
};

export default App;
