/**
 * Тестовая прога для получения понятий на React.js
 * Copyright © 2014-2017 LasGIS Company. All Rights Reserved.
 */

import React, { Component, PropTypes } from 'react';
import withStyles from 'isomorphic-style-loader/lib/withStyles';
import s from './MyReactPage.scss';
import fetch from './../../core/fetch';
import axios from 'axios';

const title = 'MyReactPage first blood';

class MyReactPage extends Component {

    constructor(props) {
        super(props);
        this.state = {jsonData: {data: "jsonData"}};
    };

    static propTypes = {
      content: PropTypes.string,
      title: PropTypes.string,
      jsonData: PropTypes.oneOfType(PropTypes.string, PropTypes.object),
    };

    static contextTypes = {
      onSetTitle: PropTypes.func.isRequired
    };

    componentWillMount() {
      this.context.onSetTitle(title);
    };

    componentDidMount() {
      this.setState({jsonData : {data: "componentDidMount"}});
      /*
       var func = async () => {
       const query = `http://gturnquist-quoters.cfapps.io/api/random`;
       const response = await fetch(query);
       const { datae } = await response.json();
       console.log('response ', response)
       };
       var data = func();
       console.log('data ', data);
       */
      /*
       fetch('http://gturnquist-quoters.cfapps.io/api/random')
       .then(function(response) {
       console.log('response ', response);
       return response.json()
       }).then(function(json) {
       jsonData = json;
       console.log('parsed json', json);
       }).catch(function(ex) {
       console.log('parsing failed', ex);
       })
       */
      axios.get('http://localhost:8099/react/manager/check1.do')
        .then(response => {
          console.log('response = ', response);
          console.log('this = ', this);
          this.setState({jsonData : response.data});
        }).catch(error => {
          console.log('error ', error);
        });
      console.log('this = ', this);
    };

    render() {
        return (
          <div className={s.root}>
            <div className={s.container}>
              <h1>{title}</h1>
              <p>{JSON.stringify(this.state.jsonData)}</p>
            </div>
          </div>
        );
    }
}

export default withStyles(MyReactPage, s);
