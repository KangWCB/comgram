import {useState, React, useEffect} from 'react';
import { BsHeart, BsHeartFill } from "react-icons/bs";
import styles from './Feed.module.css'
import axios from 'axios';

const Feed = (inherit_token) => {
    const [like, setLike] = useState(false);
    let board_Contents = [];
    let board_ImgPath = [];
    
    let acctoken = inherit_token;
    


    const getBoard = (token) => {
        axios.get('/api/boards/list', {headers : 
            {'Authorization': token}})
        .then((res) => {
            board_Contents = res.data['content'];
            board_ImgPath = res.data['savedImgPath'];
            console.log(board_Contents);
            console.log(board_ImgPath);
        })
        .catch((err) => {
            console.log(err);
        });
    };
    
    const likeHandler = () => {
        getBoard(acctoken);
        setLike(!like);
    };

    return (
        <div className={styles.content}>

        <img className={styles.photo} src="img/hi.png"></img>

        <div className={styles.comment}>
            <div className='like' onClick={likeHandler}>
            {
                like ? (<BsHeart className={styles.likeIcon}/>) : (<BsHeartFill className={styles.likeIcon}/>)
            }
            <h3>ㅋㅋ</h3>
        </div>
        </div>
    </div>
    )

};

export default Feed;