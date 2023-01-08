import {useState, React, useEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom'
import axios from 'axios';
import styles from './Login.module.css';

const LoginPage = () => {
    const [email, setEmail] = useState(""); 
    const [password, setPassword] = useState(""); 
    const [name, setName] = useState(""); 
    const [nickname, setNickname] = useState(""); 
    const [status, setStatus] = useState("");
    const [text, setText] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
      let acctoken = localStorage.getItem('accessToken');
      let islogin = localStorage.getItem('isLogin');
      if(acctoken && islogin) // 토큰 있고 로그인 성공하면 메인페이지 이동
      {
        navigate("/");
        console.log(`token: ${acctoken}`);
      }
    },[]);
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
            localStorage.setItem('accessToken', res.data[`token`]);
            axios.defaults.headers.common['x-access-token'] = res.data[`token`];
            console.log(res.data);
            localStorage.setItem('userId', res.data[`id`]);
            
            setStatus("로그인 성공");

            let acctoken = localStorage.getItem('accessToken');
            console.log(`test: ${acctoken}`);
            let asd = localStorage.getItem('userId');
            console.log(`test: ${asd}`);

            localStorage.setItem('IsLogin', true);
        })
        .catch((err) => {
            console.log(err);
            setStatus("로그인 실패");
            localStorage.setItem('IsLogin', false);
        });
    };

    const formChange = (e) => {
        const container = document.getElementById('container');
        let id = e.currentTarget.id;  
        if(id === 'signUp')
            container.classList.add(styles['right_panel_active']);
        else if(id === 'signIn')
            container.classList.remove(styles['right_panel_active']);
    };
    const testfunc = () => {
        let acctoken = localStorage.getItem('accessToken');
        axios.get('/api/members/info', {headers : 
            {'Authorization': acctoken}})
        .then((res) => {
            console.log(res.data);
            setText(res.data);
        })
        .catch((err) => {
            console.log(err);
        });
    }



    return (
    <div className={styles.body}>
    <div className={styles.container} id="container">
        <div className={`${styles.form_container} ${styles.sign_up_container}`}>
          <form className={styles.form}>
            {/*test*/}
            <h5>status:{status}</h5> 
            <h5>text:{text}</h5> 
            {/*test*/}
            <h1 className={styles.h1}>Create Account</h1>
            <div className={styles.social_container}>
              
            </div>
            <span>or use your email for registration</span>
            
            <input className={styles.input} type="email" placeholder="Email"
            onChange={(e) => setEmail((e.target.value))}/>
            <input className={styles.input} type="password" placeholder="Password"
            onChange={(e) => setPassword((e.target.value))}/>
            <input className={styles.input} type="text" placeholder="Name" 
            onChange={(e) => setName((e.target.value))}/>
            <input className={styles.input} type="text" placeholder="NickName" 
            onChange={(e) => setNickname((e.target.value))}/>
            <button className={styles.button} onClick={() => registerHandler()}>Sign Up</button>
          </form>
        </div>
        <div className={`${styles.form_container} ${styles.sign_in_container}`}>
          <form className={styles.form}>
            {/*test*/}
            <h5>status:{status}</h5> 
            <h5>text:{text}</h5> 
            {/*test*/}
            <h1>Sign in</h1>
            <div className={styles.social_container}>
              
            </div>
            <span>or use your account</span>
            <input className={styles.input} type="email" placeholder="Email"
            onChange={(e) => setEmail((e.target.value))}/>
            <input className={styles.input} type="password" placeholder="Password"
            onChange={(e) => setPassword((e.target.value))}/>
            <a href="#">Forgot your password?</a>
            <Link to="/">
              <button className={styles.button} onClick={() => loginHandler()}>Sign In</button>
            </Link>
          </form>
        </div>
        <div className={styles.overlay_container}>
          <div className={styles.overlay}>
            <div className={`${styles.overlay_panel} ${styles.overlay_left}`}>
              <h1>Welcome Back!</h1>
              <p>To keep connected with us please login with your personal info</p>
              <button className={`${styles.button} ${styles.ghost}`} id="signIn" onClick={(e) => formChange(e)}>Sign In</button>
            </div>
            <div className={`${styles.overlay_panel} ${styles.overlay_right}`}>
              <h1>Hello, Friend!</h1>
              <p>Enter your personal details and start journey with us</p>
              <button className={`${styles.button} ${styles.ghost}`} id="signUp" onClick={(e) => formChange(e)}>Sign Up</button>
            </div>   
          </div>

        </div>
      </div>
      </div>
    )
};
export default LoginPage;