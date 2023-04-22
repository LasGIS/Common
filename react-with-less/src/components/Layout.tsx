import React from 'react';
import { Outlet } from 'react-router';

const Layout = () => (
  <div className="app">
    <header className="app-header"></header>
    <div className="app-content">
      <Outlet />
    </div>
  </div>
);

export default Layout;
