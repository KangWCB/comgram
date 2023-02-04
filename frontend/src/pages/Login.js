import {useState, React, useEffect} from 'react';
import { useNavigate, useLocation, useParams} from 'react-router-dom'
import axios from 'axios';
import styles from './Login.module.css';

const LoginPage = () => {
    const [email, setEmail] = useState(""); 
    const [password, setPassword] = useState(""); 
    const [name, setName] = useState(""); 
    const [nickname, setNickname] = useState(""); 
    const [status, setStatus] = useState("");
    const navigate = useNavigate();
    const location = useLocation();
    const params = useParams();
    const kakaoLoginUrl = 'http://localhost:8080/oauth2/authorization/kakao';
    const naverLoginUrl = 'http://localhost:8080/oauth2/authorization/naver';
    const googleLoginUrl = 'http://localhost:8080/oauth2/authorization/google';
    useEffect(() => {
      console.log(location);
      console.log(params);
    },[location])

    useEffect(() => {
      let acctoken = localStorage.getItem('accessToken');
      let islogin = localStorage.getItem('isLogin');
      console.log(islogin)
      if(acctoken && islogin != 'false') // 토큰 있고 로그인 성공하면 메인페이지 이동
      {
        console.log("islogin")
        console.log(islogin)
        navigate("/",{state:
          {
            islogin : true,
          }
        });
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
        console.log(email,password)
        if(email == '' || password == '')
        {
          setStatus("이메일 혹은 패스워드가 입력되지 않았습니다.");

        }
        else
        {
          const config = {"Content-Type" : 'application/json'};
          axios.post('/api/members/login', userObject, config)
          .then((res) => {
            console.log(res.data)
              localStorage.setItem('grantType', res.data['token']['grantType']);
              localStorage.setItem('refreshToken', res.data['token']['refreshToken']);
              localStorage.setItem('accessToken', res.data['token']['accessToken']);
              axios.defaults.headers.common['x-access-token'] = res.data[`token`];
              
              localStorage.setItem('userId', res.data[`id`]);
              setStatus("로그인 성공");
              localStorage.setItem('IsLogin', true);
              navigate("/");    // 리다이렉트
          })
          .catch((err) => {
              console.log(err.toJSON());
              setStatus("로그인 실패");
              localStorage.setItem('IsLogin', false);
          });
      }
    };

    const keyHandler = (e) => {
      if(e.key === 'Enter') {
          loginHandler();
      }
  }

    const OAuth2LoginHandler = (url) => {
        window.location.href = url;
    };


    const formChange = (e) => {
        const container = document.getElementById('container');
        let id = e.currentTarget.id;  
        if(id === 'signUp')
            container.classList.add(styles['right_panel_active']);
        else if(id === 'signIn')
            container.classList.remove(styles['right_panel_active']);
    };





    return (
    <div className={styles.body}>
    <div className={styles.container} id="container">
        <div className={`${styles.form_container} ${styles.sign_up_container}`}>
          <form className={styles.form}>
            {/*test*/}
            <h5>status:{status}</h5> 

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
            <h5>{status}</h5> 

            {/*test*/}
            <h1>Sign in</h1>
            <div className={styles.social_container}>
              <img className={styles.social_login_img} onClick={() => OAuth2LoginHandler(kakaoLoginUrl)} src="/img/kakao_login_small.png"></img>
              <img className={styles.social_login_img} onClick={() => OAuth2LoginHandler(naverLoginUrl)} src="/img/naver_login.png"></img>
              <img className={styles.social_login_img} onClick={() => OAuth2LoginHandler(googleLoginUrl)} src="/img/google_login.png"></img>
            </div>
            <span>or use your account</span>
            <input className={styles.input} type="email" placeholder="Email"
            onChange={(e) => setEmail((e.target.value))}/>
            <input className={styles.input} type="password" onKeyPress={keyHandler} placeholder="Password"
            onChange={(e) => setPassword((e.target.value))}/>
            <a href="#">Forgot your password?</a>
            <button type="button" className={styles.button} onClick={() => loginHandler()}>Sign In</button>
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