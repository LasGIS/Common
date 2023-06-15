/*
 *  @(#)App.tsx  last: 15.06.2023
 *
 * Title: LG prototype for spring-security + spring-data + react
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import CommonLayout from "./Main/CommonLayout";
import CounterPage from "./Counter/CounterPage";
import LoginPage from "./Login/LoginPage";

const App = () => {
  return (
    <Router basename={process.env.PUBLIC_URL}>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<CommonLayout />}>
          <Route path="/hosts" element={<CounterPage />}/>
        </Route>
      </Routes>
    </Router>
  );
};

export default App;
