import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import UsersPage from './page/users/UsersPage';

const App = () => (
  <Router basename={process.env.PUBLIC_URL}>
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route path="users" element={<UsersPage />} />
      </Route>
    </Routes>
  </Router>
);

export default App;
