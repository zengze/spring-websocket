import {render} from 'react-dom';
import React, { Component } from 'react';
import _ from 'lodash';

import { Button, Table, Modal, Input } from 'antd';

import * as api from '../redux/services/api';

let socket = null;

class Index extends Component {

  constructor(props) {
    super(props);
    this.state = {
      year: 2016,
      data: [],
    }
  }

  componentDidMount() {

  }

  _login() {
    const { data } = this.state;
    let that = this;
    let userName = document.getElementById("userName").value;
    socket = new WebSocket('ws://localhost:8081/spring-websocket/websocket.connection?userName=' + userName);

    socket.onopen = function(event) {
      socket.onmessage = function(event) {
        data.push(<div>{event.data}</div>);
        that.setState({
          data: data,
        });
      };
    }

    socket.onerror = function(event) {
      socket.onmessage = function(event) {
        data.push(<div>{event.data}</div>);
        that.setState({
          data: data,
        });
      };
    }
  }

  _logout() {
    const { data } = this.state;
    let that = this;
    socket.close();

    socket.onclose = function(event) {
      socket.onmessage = function(event) {
        data.push(<div>{event.data}</div>);
        that.setState({
          data: data,
        });
      };
    };
  }

  _send() {
    const { data } = this.state;
    let that = this;
    let message = document.getElementById("message").value;
    socket.send(message);

    socket.onmessage = function(event) {
      data.push(<div>{event.data}</div>);
      that.setState({
        data: data,
      });
    };
  }

  render() {
    const { data } = this.state;

    return (
      <div>
        <div>
          userName:
          <Input id="userName" placeholder="input your name" />
          <Button onClick={() => this._login()}>login</Button>
          <Button onClick={() => this._logout()}>logout</Button>
        </div>
        <div>
          message:
          <Input id="message" placeholder="input your message" />
          <Button onClick={() => this._send()}>send</Button>
        </div>

        <div id="messagePanel">{data}</div>
      </div>
    );
  }
}

render(<Index />, document.getElementById('react-content'));
