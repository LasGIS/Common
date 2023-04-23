import React from 'react';
import { Outlet } from 'react-router';
import { Link } from 'react-router-dom';

const Layout = () => (
  <div className="app">
    <header className="app-header">
      <ul>
        <li>
          <Link to={'users'}>Users Page</Link>
        </li>
        <li>
          <Link to={'functional'}>Functional</Link>
        </li>
        <li>
          <Link to={'component'}>Component</Link>
        </li>
      </ul>
    </header>
    <div className="app-content">
      <Outlet />
    </div>
  </div>
);

export default Layout;
