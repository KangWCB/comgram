import {useState, React, useEffect} from 'react';
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";
import styles from './Feed.module.css'
import axios from 'axios';

const Feed = (inherit_token) => {
    const [like, setLike] = useState(false);
    const [ismoreView, setIsmoreView] = useState(false);
    const [context, setContext] = useState("abc");
    let board_Contents = [];
    let board_ImgPath = [];
    let writerName = "somebodyhelpme";
    let likeName = "newbie";
    let likeCnt = "1.2만명";
    let writeTime = "2시간 전";
    let origin_context = "";
    let cut_context = "";
    

    let acctoken = inherit_token;
    let commentList = [
        {
            userName: "sibal",
            comment: "죽여주세요 제발" 
        },
        {
            userName: "sibal2",
            comment: "죽여주세요 제발" 
        },
        {
            userName: "sibal3",
            comment: "죽여주세요 제발" 
        },
    ];



    const viewMoreHandler = () => {
        setIsmoreView(!ismoreView);
        console.log(`iv:${ismoreView}`)
    };

    const likeHandler = () => {
        setLike(!like);
    };


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
    
    

    return (
        <div className={styles.content}>
            {/* 피드 */}
            <div className={styles.profile_form}>
                <div className={styles.box}>
                    <img className={styles.profileImg} src="/img/hi2.png"></img>
                </div>
                <span className={`${styles.span} ${styles.bold} `}> {writerName}</span>
                <span className={`${styles.span} ${styles.gray} `}> •  {writeTime}</span>
            </div>

        <img className={styles.photo} src="img/hi.png"></img>
        <div className={styles.icon_form}>
            <div className={styles.likeIcon} onClick={likeHandler}>
            {
                like ? (<BsHeart className={styles.likeIcon}/>) : (<BsHeartFill className={styles.likeIcon}/>)
            }
            </div>
            <SlSpeech className={styles.icon}/>
        </div>
        <div className={styles.comment_form}>
            <span className={`${styles.span} ${styles.bold} `}>{likeName}</span><span className={styles.span}>님 </span><span className={`${styles.span} ${styles.bold}`}>외 {likeCnt}</span><span className={styles.span}>이 좋아합니다</span>
        </div>
        {/* 피드 본문 */}
        <div className={styles.context}>
        <span className={styles.span}>{context}</span>
        <button className={styles.moreViewBtn}>... 더보기</button>
        </div>
    </div>
    )

};

export default Feed;