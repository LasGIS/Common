import { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CommonLayout from './Main/CommonLayout';
import CounterPage from './Counter/CounterPage';
import LoginPage from './Login/LoginPage';
import { getAppSettings } from '../reducer/common';
import { useDispatch } from 'react-redux';
//import store, { AppDispatch } from '../reducer/store';

const App = () => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(getAppSettings());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

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
