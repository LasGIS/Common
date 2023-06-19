import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CommonLayout from './Main/CommonLayout';
import CounterPage from './Counter/CounterPage';
import LoginPage from './Login/LoginPage';

const App = () => (
  <Router /* basename={import.meta.env.BASE_URL}*/>
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/" element={<CommonLayout />}>
        <Route path="/counter" element={<CounterPage />} />
      </Route>
    </Routes>
  </Router>
);

export default App;
