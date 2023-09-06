import { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CommonLayout from './Main/CommonLayout';
import CounterPage from './Counter/CounterPage';
import LoginPage from './Login/LoginPage';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '../reducer/store';
import { getAppSettings } from '../reducer/common';

const App = () => {
  const dispatch = useDispatch<AppDispatch>();
  useEffect(() => {
    dispatch(getAppSettings() as AppDispatch);
  }, [dispatch]);

  return (
    <Router basename={process.env.PUBLIC_URL}>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<CommonLayout />}>
          <Route path="/counter" element={<CounterPage />} />
        </Route>
      </Routes>
    </Router>
  );
};

export default App;
