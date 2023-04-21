import React from 'react';
import { Outlet } from 'react-router';

const Layout = () => (
  <div className="App">
    <header className="App-header">
      <Outlet />
    </header>
  </div>
);

export default Layout;
