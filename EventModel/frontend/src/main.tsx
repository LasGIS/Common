import { Suspense } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.tsx';

createRoot(document.getElementById('root')!).render(
  <Suspense fallback={<div>Загрузка</div>}>
    <App />
  </Suspense>,
);
