import React from 'react';
import { Link } from 'react-router-dom';
import { MainContent } from '@/style/style.tsx';

const WindowPage: React.FC = () => {
  return (
    <MainContent>
      <ul>
        <li>
          <Link to="/demo">Demo</Link>
        </li>
        <li>
          <a href="http://localhost:8088/demo/index.html?" target="_blank">
            :8088/demo/index.html
          </a>
        </li>
        <li>
          <a href="http://localhost:8090/demo/index.html?" target="_blank">
            :8090/demo/index.html
          </a>
        </li>
      </ul>
    </MainContent>
  );
};

export default WindowPage;
