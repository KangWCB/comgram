import { useLocation, useNavigate } from 'react-router-dom'
import {useState, React, useEffect} from 'react';
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

        if(acctoken)
        {
            islogin = true; 
            getUserinfo(acctoken);
        }
        else {
            localStorage.removeItem("accessToken");
            navigate("/login");
        }
    },[]);

    useEffect(() => {
        localStorage.setItem('nickName', nickname);
    },[nickname])

    useEffect(() => { // 주소 변경시
        
        if(currentPath === location.pathname)
        {
            window.location.reload();
        }
            currentPath = location.pathname;
    },[location]);

    const getUserinfo = async (acctoken) => {
        let infoAPI = `${process.env.REACT_APP_BACKEND}` + '/api/members/info'
        await axios.get(infoAPI, {headers : 
            {'Authorization': acctoken}})
        .then((res) => {
            setNickname(res.data['nickname']);
        })
        .catch((err) => {
            localStorage.removeItem("accessToken");
            navigate("/login");
        });
    };



    return (
        <div className={styles.container}>
            {<Feed/> }
            {<Profile/> }
        </div>
        
    )
}

export default Mainpage;

