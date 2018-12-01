import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Row, Col, Button, Form, FormGroup, Label, Input, FormText } from 'reactstrap';
import axios from 'axios';
import qs from 'qs';

class LoginPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: 'tiendat@gmail.com',
      password: 'Test@123',
      error: '',
      successLogin: false
    };
    this.handlePassChange = this.handlePassChange.bind(this);
    this.handleUserChange = this.handleUserChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.dismissError = this.dismissError.bind(this);
  }

  dismissError() {
    this.setState({ error: '' });
  }

  handleSubmit(evt) {
    evt.preventDefault();

    if (!this.state.username) {
      return this.setState({ error: 'Username is required' });
    }

    if (!this.state.password) {
      return this.setState({ error: 'Password is required' });
    }

    let url = 'http://localhost/cattocdi.webapi/token';
    let data = {
      grant_type: 'password',
      username: this.state.username,
      password: this.state.password,
    }
    var self = this;
    axios.post(url, qs.stringify(data), {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      }
    })
      .then(function (response) {
        let expiredTime = new Date().getTime() + response.data.expires_in*1000;
        localStorage.setItem('CATTOCDI_ADMIN_TOKEN', response.data.access_token);
        localStorage.setItem('CATTOCDI_ADMIN_EXPIRES', expiredTime);
        self.setState({
          "successLogin": true
        });
      })
      .catch(function (error) {
        console.log(error);
      });

    return this.setState({ error: '' });
  }

  handleUserChange(evt) {
    this.setState({
      username: evt.target.value,
    });
  };

  handlePassChange(evt) {
    this.setState({
      password: evt.target.value,
    });
  }

  render() {
    if (this.state.successLogin) {
      return <Redirect to="/" />
    }
    // NOTE: I use data-attributes for easier E2E testing
    // but you don't need to target those (any css-selector will work)      
    return (
      <Row>
        <Col sm={{ size: 'auto', offset: 5 }}>
          <Form onSubmit={this.handleSubmit}>
            {
              this.state.error &&
              <h3 data-test="error" onClick={this.dismissError}>
                <button onClick={this.dismissError}>✖</button>
                {this.state.error}
              </h3>
            }
            <FormGroup>
              <Label for="exampleEmail">User Name</Label>
              <Input
                value={this.state.username} onChange={this.handleUserChange}
                type="email" name="email" id="exampleEmail" placeholder="with a placeholder" />
            </FormGroup>
            <FormGroup>
              <Label for="examplePassword">Password</Label>
              <Input
                value={this.state.password} onChange={this.handlePassChange}
                type="password" name="password" id="examplePassword" placeholder="password placeholder" />
            </FormGroup>
            <FormGroup check row>              
                <Button type="submit">Login</Button>              
            </FormGroup>
          </Form>
        </Col>
      </Row>
    );
  }
}

export default LoginPage;