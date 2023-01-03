import {useState, React} from 'react';
import ReactDOM from 'react-dom/client';
import axios from 'axios';
import './Login.css';

const LoginPage = () => {
    const [email, setEmail] = useState(""); 
    const [password, setPassword] = useState(""); 
    const [name, setName] = useState(""); 
    const [nickname, setNickname] = useState(""); 
    const [status, setStatus] = useState("");
    const [text, setText] = useState("");


    const registerHandler = () => {
        const userObject = {
            'email' : email,
            'password' : password,
            'name' : name,
            'nickname' : nickname,
        };
        console.log(userObject);
        const config = {"Content-Type" : 'application/json'};
        axios.post('/api/members/register', userObject, config)
        .then((res) => {
            console.log(res.data);
            setStatus("가입 성공");
        })
        .catch((err) => {
            console.log(err);
            setStatus("가입 실패");
        });
    };

    const loginHandler = () => {
        const userObject = {
            'email' : email,
            'password' : password,
        };
        const config = {"Content-Type" : 'application/json'};
        axios.post('/api/members/login', userObject, config)
        .then((res) => {
            localStorage.setItem('accessToken', res.data);
            axios.defaults.headers.common['Authorization'] = res.data;
            console.log(res.data);
            setStatus("로그인 성공");
        })
        .catch((err) => {
            console.log(err);
            setStatus("로그인 실패");
        });
    };

    const formChange = (e) => {
        const container = document.getElementById('container');
        let id = e.currentTarget.id;  
        if(id === 'signUp')
            container.classList.add("right-panel-active");
        else if(id === 'signIn')
            container.classList.remove("right-panel-active");
    };
    const testfunc = () => {
        let acctoken = localStorage.getItem('accessToken');
        axios.get('/api/members/info', {headers : 
            {'Authorization': acctoken}})
        .then((res) => {
            console.log(res.data);
            setText(res.data['nickname']);
        })
        .catch((err) => {
            console.log(err);
        });
    }



    return (
        
    <div className="container" id="container">
        <div className="form-container sign-up-container">
          <form>
            {/*test*/}
            <h5>status:{status}</h5> 
            <h5>text:{text}</h5> 
            {/*test*/}
            <h1>Create Account</h1>
            <div className="social-container">
              
            </div>
            <span>or use your email for registration</span>
            
            <input type="email" placeholder="Email"
            onChange={(e) => setEmail((e.target.value))}/>
            <input type="password" placeholder="Password"
            onChange={(e) => setPassword((e.target.value))}/>
            <input type="text" placeholder="Name" 
            onChange={(e) => setName((e.target.value))}/>
            <input type="text" placeholder="NickName" 
            onChange={(e) => setNickname((e.target.value))}/>
            <button onClick={() => registerHandler()}>Sign Up</button>
          </form>
        </div>
        <div className="form-container sign-in-container">
          <form>
            {/*test*/}
            <h5>status:{status}</h5> 
            <h5>text:{text}</h5> 
            {/*test*/}
            <h1>Sign in</h1>
            <div className="social-container">
              
            </div>
            <span>or use your account</span>
            <input type="email" placeholder="Email"
            onChange={(e) => setEmail((e.target.value))}/>
            <input type="password" placeholder="Password"
            onChange={(e) => setPassword((e.target.value))}/>
            <a href="#">Forgot your password?</a>
            <button onClick={() => loginHandler()}>Sign In</button>
          </form>
        </div>
        <div className="overlay-container">
          <div className="overlay">
            <div className="overlay-panel overlay-left">
              <h1>Welcome Back!</h1>
              <p>To keep connected with us please login with your personal info</p>
              <button className="ghost" id="signIn" onClick={(e) => formChange(e)}>Sign In</button>
            </div>
            <div className="overlay-panel overlay-right">
              <h1>Hello, Friend!</h1>
              <p>Enter your personal details and start journey with us</p>
              <button className="ghost" id="signUp" onClick={(e) => formChange(e)}>Sign Up</button>
            </div>   
          </div>

        </div>
      </div>
    )
};
export default LoginPage;