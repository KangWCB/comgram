import {Link, useLocation, useNavigate} from 'react-router-dom'
import {useState, React, useEffect} from 'react';
import ReactDOM from 'react-dom/client';
import axios from 'axios';
import styles from './Mainpage.module.css'
import Feed from '../components/Feed/Feed';
import Profile from '../components/Profile/Profile';

const Mainpage = () => {
    const [nickname, setNickname] = useState('');
    const navigate = useNavigate();
    
    let islogin = false;
    let acctoken = localStorage.getItem('accessToken');
    let location = useLocation(); // 현재 주소
    let currentPath = "";


    useEffect(() => { // 로그인 여부 검사
        let acctoken = localStorage.getItem('accessToken');
        localStorage.setItem('isLogin', false);
        console.log(acctoken)
        if(acctoken)
        {
            islogin = true; 
            getUserinfo(acctoken);
            localStorage.setItem('nickName', nickname);
            console.log(nickname)
        }
        else {
            navigate("/login");
        }
    },[]);

    useEffect(() => { // 주소 변경시
        if(currentPath === location.pathname)
            window.location.reload();
        currentPath = location.pathname;
    },[location]);




    const logoutHandler = () => {
        localStorage.removeItem("accessToken");
        console.log("remove token");
        window.location.reload();
    };

    const getUserinfo = async (acctoken) => {
        await axios.get('/api/members/info', {headers : 
            {'Authorization': acctoken}})
        .then((res) => {
            setNickname(res.data['nickname']);
        })
        .catch((err) => {
            console.log(err);
        });
    };



    return (
        <div className={styles.container}>
            <Link to="/login">
                <button>Login</button>
            </Link>
            <button onClick={() => logoutHandler()}>Logout</button>
            <br/>nick : {nickname}
            <br/>tk : {acctoken}
            {islogin && <Feed/> }
            {islogin && <Profile/> }
        </div>
        
    )
}

export default Mainpage;

