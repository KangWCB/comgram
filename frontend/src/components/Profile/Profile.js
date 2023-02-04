import React ,{ useState, useEffect, useRef } from "react";
import axios from "axios";
import styles from './Profile.module.css';
import {useNavigate} from 'react-router-dom'
import ModifyProfile from "./ModifyProfile";
import { BsPencilSquare } from "react-icons/bs"

const Profile = () => {
    const acctoken = localStorage.getItem('accessToken');
    const [nickname, setNickname] = useState('');
    const [profileImgPath, setProfileImgPath] = useState('');
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    
    const navigate = useNavigate();
    const modifyRef = useRef();

    useEffect(() => {
        console.log("hi")
        getUserinfo(acctoken);
    },[]);

    const getUserinfo = async (acctoken) => {
        await axios.get('/api/members/info', {headers : 
            {'Authorization': acctoken}})
        .then((res) => {
            setNickname(res.data['nickname']);
            setName(res.data['name']);
            console.log(res.data);
            let tmp_path = res.data['profilePhotoUrl'].replace(/\"/gi,"");
            let tmp_idx = tmp_path.indexOf("tmp");
            tmp_path = tmp_path.substring(tmp_idx);
            setProfileImgPath(tmp_path);
            setEmail(res.data['email']);
        })
        .catch((err) => {
            navigate("/login");
        });
    };

    

    const openModalHandler = () => {
        modifyRef.current?.modalHandler(true);
    }

    const infoHandler = () => {
        navigate('/info')
    }


    let intromsg = "안녕하세요 박사지망 석사 진행중인 정래원입니다."

    return (
        <div className={styles.container}>
                <div className={styles.box}>
                    <img onClick={infoHandler} className={styles.profileImg} src={`${profileImgPath}`}/>
                </div>
                <div className={styles.profile_container}>
                    <span className={`${styles.span_email}`}>{email}</span>
                    <br/><span className={`${styles.span} ${styles.gray} `}>{nickname}</span>
                </div>
                <div className={styles.intro_container}>
                        <span className={`${styles.span_intro} ${styles.gray} `}>{intromsg}</span>
                    </div>
                <div onClick={openModalHandler} className={styles.modify_container}>
                    <BsPencilSquare style={{fontSize: '30px'}}/>
                    <span className={styles.span_profile}>프로필 수정</span>
                </div>

                <ModifyProfile name={name} nickname={nickname} ref={modifyRef}/>
        </div>
    )

};

export default Profile;