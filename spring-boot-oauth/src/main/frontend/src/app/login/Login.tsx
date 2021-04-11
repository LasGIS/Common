import './Login.css';
import React, { Component } from "react";
import { get, post } from "jquery";

type Props = {};

type State = {
  user: string;
  authenticated: boolean;
};

class Login extends Component<Props, State> {

  constructor(props: any) {
    super(props);
    this.logout = this.logout.bind(this);
    this.state = {
      user: "",
      authenticated: false
    };
  }

  componentDidMount() {
    get({
      url: "/user",
      success: data => {
        this.setState({
          user: data.name,
          authenticated: data.authenticated
        });
      }
    });
  };

  logout(p1: React.MouseEvent<HTMLButtonElement>) {
    console.log("logout", p1);
    post("/logout").done(() => {
      this.setState({
        user: "",
        authenticated: false
      });
    });
  };

  render() {
    const { user, authenticated } = this.state;
    return (
      <div className="Login">
        <h1>Login</h1>
        {!authenticated &&
        <div className="container">
          With GitHub:
          <a href="/oauth2/authorization/github">click here</a>
        </div>
        }
        {authenticated &&
        <div className="container">
          Logged in as: <span id="user">{user}</span>
          <div>
            <button onClick={this.logout} className="btn btn-primary">Logout</button>
          </div>
        </div>
        }
      </div>
    );
  }
}

export default Login;
