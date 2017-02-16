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

  static propTypes = {
    content: PropTypes.string,
    title: PropTypes.string,
    jsonData: PropTypes.string,
  };

  static contextTypes = {
    onSetTitle: PropTypes.func.isRequired
  };

  componentWillMount() {
    this.context.onSetTitle(title);
  };

  componentDidMount() {
    this.jsonData = {data: "jsonData"};
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
    axios.get('http://localhost:8099/react/manager/check.do'/*, {
      baseURL: 'http://gturnquist-quoters.cfapps.io/api/',
      headers: {'Access-Control-Allow-Origin': 'http://localhost:3001'}
    }*/).then(function (response) {
      console.log('response ', response);
    }).catch(function (error) {
      console.log('error ', error);
    });
  };

  render() {
    const {...prps } = this.props;
    return (
      <div className={s.root}>
        <div className={s.container}>
          <h1>{title}</h1>
          <p>{JSON.stringify(this.jsonData)}</p>
          <p>GET /api/random HTTP/1.1
          Host: gturnquist-quoters.cfapps.io
          Connection: keep-alive
          Pragma: no-cache
          Cache-Control: no-cache
          Upgrade-Insecure-Requests: 1
          User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36
          Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/\*;q=0.8
          Accept-Encoding: gzip, deflate, sdch
          Accept-Language: ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4</p>
        </div>
      </div>
    );
  }

}

export default withStyles(MyReactPage, s);
