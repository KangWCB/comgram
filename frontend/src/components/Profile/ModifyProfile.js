import styles from './ModifyProfile.module.css'
import React, {useState, useEffect, forwardRef, useImperativeHandle} from 'react'
import Modal from 'react-modal';
import { useSelector, useDispatch } from 'react-redux';
import axios from 'axios';
import { addPostobj, updatePostobj } from '../../redux/action';
import { IoMdClose } from 'react-icons/io'
import moment from "moment";
import { useNavigate } from 'react-router-dom';

const ModifyProfile = forwardRef(({name, nickname}, modifyRef) => {
    const [ModalisOpen, setModalisOpen] = useState(false);
    const [profileImg, setProfileImg] = useState();
    const [newNickname, setNewNickname] = useState('');
    const [encodeImg,setEncodeImg] = useState('');

    const acctoken = localStorage.getItem('accessToken');
    const userid = localStorage.getItem('userId');

    useImperativeHandle(modifyRef, () => ({
        modalHandler: (bool) => {
            setModalisOpen(bool);
        }
    }))
    
    useEffect(() => {
        setNewNickname(nickname);
    },[])
    const imgHandler = (e) => {
        setProfileImg(e.target.files[0]);
        imgEncoder(e.target.files[0]);
    };

    const imgEncoder = (file) => {
        const uploadTextId = document.getElementById('uploadText')
        const reader = new FileReader();
        reader.readAsDataURL(file);
        return new Promise((resolve) => {
            reader.onload = () => {
                uploadTextId.style.display = 'none';
                setEncodeImg(reader.result);
                resolve();
            };
        });
    };
    

    const modifyProfile = () => {
        console.log(acctoken);
        console.log("호출");
        console.log(userid)
        const formData = new FormData();
        formData.append('photo', profileImg);
        formData.append('nickname', newNickname)
        console.log(formData.get('photo'))
        let profileUpdateAPI = `/api/members/${userid}/update`
        axios.post(profileUpdateAPI, formData,{
            headers: {
                'Authorization': acctoken,
                'Content-Type': 'multipart/form-data',
            }}
        )
        .then(res => {
            setModalisOpen(false);
            window.location.href="/";
        })
        .catch(err => {
            console.log(err);
        })
    };

    const newNicknameHandler = (e) => {
        setNewNickname(e.target.value);
    }

    const modalStyle = {
        overlay: {
            zIndex: 5,
            backgroundColor: 'rgba(0,0,0,0.25)'
        },
        content: {
            padding: '0px',    
            transform: 'translateX(50%)',
            width: '50%',
            height: '70%',
            padding: '0px'
        }
    }
    const modalcloseHandler = () => {
        setModalisOpen(false)
        setProfileImg('')
        setEncodeImg('')
        setNewNickname('')
    }

    return (
        <Modal style={modalStyle} isOpen={ModalisOpen} onRequestClose={modalcloseHandler} ariaHideApp={false}> 
            <div className={styles.container}>
                    <div className={styles.title_container}>
                    <span className={styles.title}>프로필 수정</span>
                    </div>
                    <div className={styles.img_container}>
                        
                        <div className={styles.box}>
                            
                            <span id="uploadText" className={styles.uploadtext}>Upload your daily life!</span>
                            {encodeImg && <img src={encodeImg}className={styles.profileImg}></img>}
                        </div>
                        
                        <input id="img_input" style={{display:"none"}} onChange={(e) => imgHandler(e)} type="file" accept="image/*" ></input>
                        <label className={styles.img_label} htmlFor='img_input'>사진 업로드</label>
                    </div>
                
                    <div className={styles.info_container}>
                        
                        <br/>
                        <div style={{marginBottom:'10px'}}>
                        <span style={{fontWeight:'bold', fontSize:'12px'}}>이름</span>
                            <br/><span>{name}</span>
                        </div>
                        <div>
                            <span style={{fontWeight:'bold', fontSize:'12px'}}>닉네임</span>
                            <br/><input className={styles.nickinput} value={nickname }onChange={(e) => newNicknameHandler(e)} defaultValue={nickname}></input>
                        </div>

                    </div>
            </div>
            <div style={{marginTop:'10px',textAlign:'center'}}>
                <button className={styles.submitBtn} 
                        disabled={profileImg === '' ? true : false} onClick={() => modifyProfile(profileImg)}>프로필 수정</button>
            </div>
        </Modal>
    )
});

export default ModifyProfile;