import React ,{ useState, useEffect } from "react";
import axios from "axios";
import styles from './Profile.module.css';
import {useNavigate} from 'react-router-dom'

const Profile = (nick, ppath) => {
    const acctoken = localStorage.getItem('accessToken');
    const userid = localStorage.getItem('userId');
    const [nickname, setNickname] = useState('');
    const [profileImgPath, setProfileImgPath] = useState('');
    const [profileImg, setProfileImg] = useState();
    const [email, setEmail] = useState('');
    const navigate = useNavigate();


    useEffect(() => {
        console.log("hi")
        getUserinfo(acctoken);
    },[]);

    const getUserinfo = async (acctoken) => {
        await axios.get('/api/members/info', {headers : 
            {'Authorization': acctoken}})
        .then((res) => {
            setNickname(res.data['nickname']);
            console.log(res.data['profilePhotoUrl']);
            setProfileImgPath(res.data['profilePhotoUrl'].replace(/\"/gi,""));
            setEmail(res.data['email']);
        })
        .catch((err) => {
            navigate("/login");
        });
    };

    const imgHandler = (e) => {
        setProfileImg(e.target.files[0]);
    };
    

    const modifyProfile = (token, img) => {
        console.log("호출");
        const formData = new FormData();
        formData.append('photo', img);
        let profileUpdateAPI = `api/members/${userid}/update`
        axios({
            url: profileUpdateAPI,
            method: 'POST',
            data: formData,
            Headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization': token,
            }
        })
        .then(res => {
            console.log(`${res.data}`);
        })
        .catch(err => {
            console.log(err);
        })
    };

    return (
        <div className={styles.container}>
                <div className={styles.box}>
                    <img className={styles.profileImg} src={`${profileImgPath}`}/>
                </div>
                <div className={styles.profile_container}>
                    <span className={`${styles.email_span}`}>{email}</span>
                    <br/><span className={`${styles.span} ${styles.gray} `}>{nickname}</span>
                </div>
                
                <input onChange={(e) => imgHandler(e)}type="file" accept="image/*" ></input>
                <button onClick={() => modifyProfile(acctoken, profileImg)}>업로드</button>
        </div>
    )

};

export default Profile;