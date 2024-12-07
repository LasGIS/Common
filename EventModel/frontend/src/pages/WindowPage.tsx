import React from 'react';
import { Link } from 'react-router-dom';

const WindowPage: React.FC = () => {
  return (
    <div>
      <h1>Vite + React</h1>
      <ul>
        <li><Link to="/demo">Demo</Link></li>
        <li><a href="http://localhost:8088/demo/index.html?" target="_blank">demo/index.html</a></li>
      </ul>
    </div>
  );
};

export default WindowPage;
