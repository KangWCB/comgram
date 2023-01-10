import {useState, React, useEffect} from 'react';

import axios from 'axios';
import styles from './Write.module.css'

const Write = () => {
    const acctoken = localStorage.getItem('accessToken');
    const [boardImg,setBoardImg] = useState('');
    const [content, setContent] = useState('');
    const imgHandler = (e) => {
        setBoardImg(e.target.files[0]);
    };
    

    const writeBoardHandler = () => {
        const acctoken = localStorage.getItem('accessToken');

        const formData = new FormData();
        formData.append('content', content);
        formData.append('photo', boardImg);
        console.log(formData.get('content'));
        console.log(formData.get('photo'));
        let api = '/api/boards/write'
        /*axios({
            url: api,
            method: 'POST',
            data: formData,
            Headers: {
                'Authorization': acctoken,
                'Content-Type': 'multipart/form-data',
            }
        })
        */
       axios.post(api,formData,{
            headers: {
                'Authorization': acctoken,
                'Content-Type': 'multipart/form-data',
            }
       })
        .then(res => {
            console.log(`${res.data}`);
        })
        .catch(err => {
            console.log(err);
        })
    };

    return(
        <div className={styles.Container}>
            <input onChange={(e) => imgHandler(e)}type="file" accept="image/*" ></input>
            <input onChange={(e) => setContent((e.target.value))}></input>
            <button onClick={writeBoardHandler}>글쓰기</button>
        </div>
    )
}
export default Write;
