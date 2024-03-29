import {useState, React, useEffect} from 'react';
import Modal from 'react-modal';
import axios from 'axios';
import styles from './Write.module.css'

import { addPostobj } from '../redux/action'
import { useDispatch } from 'react-redux';

const Write = () => {
    const acctoken = localStorage.getItem('accessToken');
    const [ModalisOpen, setModalisOpen] = useState(true);
    const [boardImg,setBoardImg] = useState('');
    const [encodeImg,setEncodeImg] = useState('');
    const [content, setContent] = useState('');
    const dispatch = useDispatch();

    const imgHandler = (e) => {
        setBoardImg(e.target.files[0]);
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

    const writeBoardHandler = () => {
        const acctoken = localStorage.getItem('accessToken');

        const formData = new FormData();
        formData.append('content', content);
        formData.append('photo', boardImg);
        let writeAPI = `${process.env.REACT_APP_BACKEND}` + '/api/boards/write'

       axios.post(writeAPI,formData,{
            headers: {
                'Authorization': acctoken,
                'Content-Type': 'multipart/form-data',
            }
       })
        .then(res => {
            dispatch(addPostobj());
            window.location.href="/";
            //window.location.replace("/");
        })
        .catch(err => {
            console.log(err);
            //navigate("/login");
        })
    };

    const contentHandler = (e) => {
        setContent(e.target.value)
    }
    const modalStyle = {
        overlay: {
            zIndex: 5,
            
            backgroundColor: 'rgba(0,0,0,0.25)'
        },
        content: {
            transform: 'translateX(50%)',
            width: '50%',
            padding: '0px'
        },    
    }

    const modalcloseHandler = () => {
        setModalisOpen(false)
        window.location.href="/";
    }

    return(
        <Modal style={modalStyle} isOpen={ModalisOpen} onRequestClose={modalcloseHandler} ariaHideApp={false}>
            <div className={styles.container}>
                <div className={styles.title_container}>
                <span className={styles.title}>글 작성</span>
                </div>
                
                <div className={styles.img_container}>
                    
                    <div className={styles.imgbox}>
                        
                        <span id="uploadText" className={styles.uploadtext}>Upload your daily life!</span>
                        {encodeImg && <img src={encodeImg}className={styles.boardImg}></img>}
                    </div>
                    <label className={styles.img_label} htmlFor='img_input'>사진 업로드</label>
                    <input id="img_input" style={{display:"none"}}onChange={(e) => imgHandler(e)}type="file" accept="image/*" ></input>
                </div>
                <div className={styles.context_container}>
                    <textarea className={styles.textarea} placeholder="본문을 입력하세요."onChange={(e) => contentHandler(e)}></textarea>
                    
                    <button className={styles.submitBtn} disabled={boardImg === '' || content === '' ? true : false} onClick={writeBoardHandler}>글쓰기</button>
                </div>
            </div>
        </Modal>
    )
}
export default Write;
