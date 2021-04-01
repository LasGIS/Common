/*
 * Copyright (c) 2021. LasGIS
 */

import React from "react";
import ReactDOM from 'react-dom';
import {fetchEmployees} from "./client";
import {EmployeeList} from "./employeeList";

export class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {employees: []};
  }

  componentDidMount() {
    fetchEmployees().done(response => {
      this.setState({employees: response.entity._embedded.employees});
    });
  }

  render() {
    return (
      <EmployeeList employees={this.state.employees}/>
    );
  }
}
