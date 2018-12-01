import React from "react";
import {
  BrowserRouter,
  Route,
  Link,
  Redirect,
  withRouter,
  Switch
} from 'react-router-dom'

import App from "./App";
import Login from './pages/Login';
import Home from './pages/Home';
import List from './pages/List';
import decode from 'jwt-decode';

const checkAuth = () => {
  let token = localStorage.getItem("CATTOCDI_ADMIN_TOKEN");
  let expired = Number(localStorage.getItem("CATTOCDI_ADMIN_EXPIRES"));  

  if (!token || !expired) {   
    return false;
  }
  
  try {    
     let now = new Date().getTime();
     console.log(expired);
     console.log(now);     
     if (expired < now) {
       console.log("OUt");
       return false;     
      } 
  }catch(ex) {
    console.log(ex);
    return false;
  }
  return true;
}

const AuthRoute = ({ component: Component, ...rest }) => (
  <Route {...rest} render={(props) => (
    checkAuth()
      ? <Component {...props} />
      : <Redirect to='/login' />
  )} />
)

const routes = (
  <BrowserRouter>
    <Switch>                  
      <Route exact path="/login" component={Login} />      
      <AuthRoute exact path="/" component={Home} />
    </Switch>
  </BrowserRouter>
);

export default routes;