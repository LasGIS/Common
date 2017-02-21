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
    this.state = {
      jsonData: {data: "jsonData"},
      error: null
    };
    // This binding is necessary to make `this` work in the callback
    this.activateLasers = this.activateLasers.bind(this);
  }

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
  }

  componentDidMount() {
/*
    (async () => {
      const query = 'http://vlaskin-2.omsk.luxoft.com:8099/react/manager/check1.do';
      const response = await fetch(query);
      const text = await response.json().catch(error => {
        console.log('error ', error);
      });
      this.setState({jsonData: text});
      console.log('text = "', text, '"; typeOf(text) = ', typeof(text));
    })();
*/
    fetch('http://vlaskin-2.omsk.luxoft.com:8099/react/manager/check1.do')
      .then( response => {
        console.log('response ', response);
        return response.json();
      }).then( json => {
        console.log('parsed json', json);
        this.setState({jsonData: json});
      }).catch( error => {
        console.log('parsing failed', error);
      });

/*
    axios.get('http://vlaskin-2.omsk.luxoft.com:8099/react/manager/check1.do')
      .then(response => {
        console.log('response = ', response);
        console.log('this = ', this);
        this.setState({jsonData: response.data});
      }).catch(error => {
        console.log('error ', error);
      });
    console.log('this = ', this);
*/
  }

  activateLasers() {
    let user = {
      firstName: "firstName",
      midName: "midName",
      lastName: "lastName",
      login: "login"
    };
    fetch('http://vlaskin-2.omsk.luxoft.com:8099/react/manager/check.do', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(user)
    }).then(response => {
      console.log('response = ', response);
      console.log('this = ', this);
      this.setState({jsonData: response.json});
    }).catch(error => {
      console.log('error ', error);
    });
    console.log('this = ', this);
  }

  render() {
    return (
      <div className={s.root}>
        <div className={s.container}>
          <h1>{title}</h1>
          <p>{JSON.stringify(this.state.jsonData)}</p>
          <button className={s.button} onClick={this.activateLasers}>
            Activate Lasers
          </button>
        </div>
      </div>
    );
  }
}

export default withStyles(MyReactPage, s);
