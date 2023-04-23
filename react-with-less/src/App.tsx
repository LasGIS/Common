import React from 'react';
import './app.scss';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import UsersPage from './page/users/UsersPage';
import SomeFunctional from './page/functional/SomeFunctional';
import SomeComponent from './page/component/SomeComponent';

const App = () => (
  <Router basename={process.env.PUBLIC_URL}>
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route path="users" element={<UsersPage />} />
        <Route path="functional" element={<SomeFunctional />} />
        <Route path="component" element={<SomeComponent />} />
      </Route>
    </Routes>
  </Router>
);

export default App;
