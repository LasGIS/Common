import './Login.css';

function Login() {
  return (
    <div className="Login">
      <h1>Login</h1>
      <div className="container unauthenticated">
        With GitHub: <a href="/oauth2/authorization/github">click here</a>
      </div>
      {/*style={{display: "none"}}*/}
      <div className="container authenticated">
        Logged in as: <span id="user"/>
{/*
        <div>
          <button onClick="logout()" className="btn btn-primary">Logout</button>
        </div>
*/}
      </div>
    </div>
  );
}

export default Login;
