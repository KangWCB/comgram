import axios from "axios";
import {useState, React, useEffect} from 'react';
import styles from './Feed.module.css'
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";

const Post = (postobj) => {
    const acctoken = localStorage.getItem('accessToken');
    const [like, setLike] = useState(false);
    const [ismoreView, setIsmoreView] = useState(false);
    const [viewMoreText, setViewMoreText] = useState("... 더 보기");
    const [originContent,setOriginContent] = useState('');
    // postobj
    const [writerName, setWriterName] = useState("");
    const [content, setContent] = useState('')
    const [contentImgPath, setContentImgPath] = useState('');
    const [postid, setPostId] = useState(0);
    const [likeUserNickName, setLikeUserNickName] = useState('hi');
    const [commentCount, setCommentCount] = useState(0);
    const [pushLike, setPushLike] = useState(false);
    const [regTime, setRegTime] = useState(null);
    const [likeCount,setLikeCount] = useState(0);

    let rd = '';
    let likeName = "newbie";
    let writeTime = "2시간 전";
    let origin_content = "";
    let cut_content = "";
    let commentList = [
        {
            userName: "sibal",
            comment: "죽여주세요 제발n" 
        },
        {
            userName: "sibal2",
            comment: "죽여주세요 제발2" 
        },
        {
            userName: "sibal3",
            comment: "죽여주세요 제발3" 
        },
    ];

    useEffect(() => {
        console.log(postobj);
        let postInfo = postobj['postobj'];
        
    /*
        console.log(postInfo['content']);
        setWriterName(postInfo['nickName']);
        setContent(postInfo['content']);
        setContentImgPath(postInfo['contentImgPath']);
        setPostId(postInfo['PostId']);
        setCommentCount(postInfo['CommentCount']);
        setPushLike(postInfo['PushLike']);
        setRegTime(postInfo['RegTime']);
        if(postInfo['boardMainLikeInfo'])
            setLikeUserNickName(postInfo['boardMainLikeInfo']['likeUserNickName']);
        */

    },[postobj]);

    const likeHandler = () => {
        setLike(!like);
    };

    

    useEffect(() => {
        contentHandler(content);
    },[ismoreView]);


    const contentHandler = (content) => {
        let limit = 50;
        if(content.length > limit)
        {
            if(!ismoreView)// ismoreview가 false면
            {
                cut_content = content.slice(0,limit);
                setOriginContent(content);
                console.log(origin_content)
                setContent(cut_content);
            }
            else if(ismoreView)
            {
                if(!content)
                    setContent(originContent);
            }
        }
        
    }

    const viewMoreHandler = () => {
        if(ismoreView)
        {
            setIsmoreView(!ismoreView);
            setViewMoreText("... 더 보기");
        }
        else
        {
            setIsmoreView(!ismoreView);
            
            setViewMoreText("접기");
        }
    };

    const commentHandler = commentList.map((data,idx) => <li key={idx}>
    <span className={`${styles.comment_span} ${styles.bold} `}>{data.userName}</span> 
    <span className={`${styles.comment_span}`}> {data.comment}</span>
    </li>)

    return(
        <div>
            {/* 피드 */}
            <div className={styles.profile_form}>
                <div className={styles.box}>
                    <img className={styles.profileImg} src="/img/hi2.png"></img>
                </div>
                <span className={`${styles.span} ${styles.bold} `}> {writerName}</span>
                <span className={`${styles.span} ${styles.gray} `}> •  {writeTime}</span>
            </div>

        <img className={styles.photo} src={contentImgPath}></img>
        <div className={styles.icon_form}>
            <div className={styles.likeIcon} onClick={likeHandler}>
            {
                like ? (<BsHeart className={styles.likeIcon}/>) : (<BsHeartFill className={styles.likeIcon}/>)
            }
            </div>
            <SlSpeech className={styles.icon}/>
        </div>
        <div className={styles.comment_form}>
            <span className={`${styles.span} ${styles.bold} `}>{likeUserNickName}</span><span className={styles.span}>님 </span><span className={`${styles.span} ${styles.bold}`}>외 {likeCount}명</span><span className={styles.span}>이 좋아합니다</span>
        </div>
   
        <div className={styles.content}>
        <span className={styles.span}>{content}</span>
        <button className={styles.tpBtn} onClick={viewMoreHandler}>{viewMoreText}</button>
        </div>

        <div className={styles.content}>
        <button className={styles.tpBtn}>댓글 {commentCount}개 모두 보기</button>
        <ul className={styles.comment_ul}>{commentHandler}</ul> 
        <button className={styles.tpBtn}>댓글 쓰기</button>       
        </div>
        
        </div>
    )

};

export default Post;