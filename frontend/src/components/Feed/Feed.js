import {useState, React, useEffect} from 'react';
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";
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
            board_Contents = res.data[['content']];
            board_ImgPath = res.data['savedImgPath'];
            console.log(res.data);
            console.log(`content:${board_Contents}`);
            console.log(`img:${board_ImgPath}`);
        })
        .catch((err) => {
            console.log(err);
        });
    };
    
    const likeHandler = () => {
        setLike(!like);
    };

    return (
        <div className={styles.content}>

        <img className={styles.photo} src="img/hi.png"></img>

        <div className={styles.comment}>
            <div className={styles.likeIcon} onClick={likeHandler}>
            {
                like ? (<BsHeart className={styles.likeIcon}/>) : (<BsHeartFill className={styles.likeIcon}/>)
            }
            </div>
            <SlSpeech/>
        </div>
    </div>
    )

};

export default Feed;