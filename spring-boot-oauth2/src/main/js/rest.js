/*
 * Copyright (c) 2021. LasGIS
 */

import $ from 'jquery';

const HTTP_METHODS = {
  GET: 'GET',
  POST: 'POST',
  PUT: 'PUT',
  DELETE: 'DELETE'
}

export const get = (url, data, requestSettings) =>
  requestDecorator(url, HTTP_METHODS.GET, data, requestSettings);

export const post = (url, data, requestSettings) =>
  requestDecorator(url, HTTP_METHODS.POST, data, requestSettings);

export const put = (url, data, requestSettings) =>
  requestDecorator(url, HTTP_METHODS.PUT, data, requestSettings);

export const del = (url, data, requestSettings) =>
  requestDecorator(url, HTTP_METHODS.DELETE, data, requestSettings);

const requestDecorator = (
  url, method, data,  requestSettings = { requestSettingsDataParam: 'json' },
) => {
  return request(url, method, data, requestSettings)
    .then(response => {
      return response;
    })
    .catch(error => {
      return error;
    });
};

export function request(url, method, data = {}, requestSettings = {}) {
  const { contentType, dataType, requestSettingsDataParam } = requestSettings;

  return new Promise((resolve, reject) => {
    $.ajax({
      url: url,
      type: method,
      contentType: contentType === 'false' ? false : contentType || 'application/json',
      dataType: dataType || 'json',
      data: getDataParam(),
      // headers: {
      //   'auth-Token': localStorage.getItem(FRONT_AUTH_TOKEN) || undefined,
      // },
    })
      .done((response) => resolve(response))
      .fail((error) => reject(error));
  });

  function getDataParam() {
    const defaultValue = method === HTTP_METHODS.GET ? data : JSON.stringify(data);
    return requestSettingsDataParam === 'json' ? defaultValue : data;
  }
}

function requestWithHeaders(url, method, data, requestSettings = {}) {
  const { contentType, dataType, requestSettingsDataParam } = requestSettings;

  return new Promise((resolve, reject) => {
    $.ajax({
      url: url,
      type: method,
      contentType: contentType === 'false' ? false : contentType || 'application/json',
      dataType: dataType || 'json',
      data: getDataParam(),
    })
      .done((response) => {
        return resolve(response);
      })
      .fail((error) => reject(error));
  });

  function getDataParam() {
    const defaultValue = method === HTTP_METHODS.GET ? data : JSON.stringify(data);
    return requestSettingsDataParam === 'json' ? defaultValue : data;
  }
}
