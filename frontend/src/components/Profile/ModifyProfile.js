import styles from './ModifyProfile.module.css'
import React, {useState, useEffect, forwardRef, useImperativeHandle} from 'react'
import Modal from 'react-modal';
import axios from 'axios';


const ModifyProfile = forwardRef(({name, nickname, introMsg, oldProfileImg}, modifyRef) => {
    const [ModalisOpen, setModalisOpen] = useState(false);
    const [profileImg, setProfileImg] = useState();
    const [newNickname, setNewNickname] = useState(null);
    const [newIntroMsg, setNewIntroMsg] = useState(null);
    const [imgsrc, setImgsrc] = useState('');
    const acctoken = localStorage.getItem('accessToken');
    const userid = localStorage.getItem('userId');
    
    useImperativeHandle(modifyRef, () => ({
        modalHandler: (bool) => {
            setModalisOpen(bool);
        }
    }))
    
    useEffect(() => {
        setNewNickname(nickname);
    },[nickname])


    useEffect(() => {
        setImgsrc(oldProfileImg);
    },[ModalisOpen])

    const imgHandler = (e) => {
        setProfileImg(e.target.files[0]);
        imgEncoder(e.target.files[0]);
    };

    const imgEncoder = (file) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        return new Promise((resolve) => {
            reader.onload = () => {
                setImgsrc(reader.result);
                resolve();
            };
        });
    };
    

    const modifyProfile = () => {
        const formData = new FormData();
        formData.append('photo', profileImg);
        if(newNickname == null)
            formData.append('nickname', nickname);
        else
            formData.append('nickname', newNickname);

        if(newIntroMsg == null)
            formData.append('introMsg', introMsg);
        else
            formData.append('introMsg', newIntroMsg);

        let profileUpdateAPI = `${process.env.REACT_APP_BACKEND}` + `/api/members/${userid}/update`
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

    const newIntroMsgHandler = (e) => {
        setNewIntroMsg(e.target.value);
    }

    const modalStyle = {
        overlay: {
            zIndex: 5,
            backgroundColor: 'rgba(0,0,0,0.25)'
        },
        content: {
            padding: '0px',    
            transform: 'translateX(50%)',
            minWidth: '500px',
            width: '50%',
            
            minHeight: '600px',
            height: '70vh',
            padding: '0px'
        }
    }
    const modalcloseHandler = () => {
        setModalisOpen(false)
    }

    return (
        <Modal style={modalStyle} isOpen={ModalisOpen} onRequestClose={modalcloseHandler} ariaHideApp={false}> 
            <div className={styles.container}>
                    <div className={styles.title_container}>
                    <span className={styles.title}>프로필 수정</span>
                    </div>
                    <div className={styles.img_container}>
                        
                        <div className={styles.box}>
                            <img src={imgsrc} className={styles.profileImg}></img>
                        </div>
                        
                        <input id="img_input" style={{display:"none"}} onChange={(e) => imgHandler(e)} type="file" accept="image/*" ></input>
                        <label className={styles.img_label} htmlFor='img_input'>사진 업로드</label>
                    </div>
                
                    <div className={styles.info_container}>
                        
                        <br/>
                        <div style={{marginBottom:'10px'}}>
                        <span className={styles.span_info}>이름</span>
                            <span> {name}</span>
                        </div>
                        <div style={{marginBottom:'10px'}}>
                            <span className={styles.span_info}>닉네임</span>
                            <input className={styles.infoinput} onChange={(e) => newNicknameHandler(e)} defaultValue={nickname}></input>
                        </div>
                        <div style={{marginBottom:'10px'}}>
                            <span className={styles.span_info}>소개</span>
                            <input className={styles.infoinput} onChange={(e) => newIntroMsgHandler(e)} defaultValue={introMsg}></input>
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