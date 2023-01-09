import axios from "axios";
import {useState, React, useEffect} from 'react';
import styles from './Feed.module.css'
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";
import moment from "moment";
import reactMoment from "react-moment";

const Post = (postobj) => {
    const acctoken = localStorage.getItem('accessToken');
    const [like, setLike] = useState(false);
    const [ismoreView, setIsmoreView] = useState(false);
    const [viewMoreText, setViewMoreText] = useState("... 더 보기");
    const [originContent,setOriginContent] = useState('');
    // postobj
    const [writerName, setWriterName] = useState("");
    const [content, setContent] = useState('')
    const [contentImgPath, setContentImgPath] = useState(null);
    const [imgHash, setimgHash] = useState('');
    const [postid, setPostId] = useState(0);
    const [likeUserNickName, setLikeUserNickName] = useState('hi');
    const [commentCount, setCommentCount] = useState(0);
    const [pushLike, setPushLike] = useState(false);
    const [regTime, setRegTime] = useState(null);
    const [likeCount,setLikeCount] = useState(0);
    const [commentContext,setCommentContext] = useState("");
    const [commentUserNickname, setCommentUserNickname] = useState("");
    const [profileImgPath,setProfileImgPath] = useState('');
    let rd = '';
    let likeName = "newbie";
    let writeTime = "시간 전";
    let origin_content = "";
    let cut_content = "";
    let commentList = [
        {
            userName: "sibal",
            comment: "죽여주세요 제발n" 
        },

    ];
    
    useEffect(() => {
        console.log(postobj);
        let postInfo = postobj['postobj'];
        


        console.log(postInfo['contentImgPath']);
        //console.log(postInfo['profileImgPath'].replace(/\"/gi,""));
        let tmp_path = postInfo['contentImgPath'];
        let tmp_idx = tmp_path.indexOf("img");
        tmp_path = tmp_path.substring(tmp_idx);
        setContentImgPath(tmp_path);        
        setWriterName(postInfo['nickName']);
        
        setContent(postInfo['content']);
        setPostId(postInfo['PostId']);
        setCommentCount(postInfo['CommentCount']);
        setPushLike(postInfo['PushLike']);
        setRegTime(postInfo['regTime']);
        setLikeCount(postInfo['likeCount']);
        setProfileImgPath(postInfo['profileImgPath'].replace(/\"/gi,""));
        if(postInfo['boardMainCommentInfo'])
        {
            setCommentContext(postInfo['boardMainCommentInfo']['commentContext']);
            setCommentUserNickname(postInfo['boardMainCommentInfo']['commentUserNickname']);
        }
        if(postInfo['boardMainLikeInfo'])
        {
            setLikeUserNickName(postInfo['boardMainLikeInfo']['likeUserNickName']);
        }
            console.log(`pi: ${profileImgPath}`);
        writetimeHandler(regTime);
        setimgHash(Date.now());
        console.log(imgHash);
        console.log(profileImgPath);
        },[postobj]);


    const likeHandler = () => {
        setLike(!like);
    };

    const writetimeHandler = (regTime) => {
        const nowTime = moment()
        writeTime = regTime;
        console.log(regTime);
        console.log(nowTime);
    }

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

    const imgHandler = () => {
        console.log("img");
        document.getElementById("ci").src = contentImgPath;
    }

    /*const commentHandler = commentList.map((data,idx) => <li key={idx}>
    <span className={`${styles.comment_span} ${styles.bold} `}>{data.userName}</span> 
    <span className={`${styles.comment_span}`}> {data.comment}</span>
    </li>)*/

    return(
        <div>
            {/* 피드 */}
            <div className={styles.profile_form}>
                <div className={styles.box}>
                    <img className={styles.profileImg} src={`${profileImgPath}`}/>
                </div>
                <span className={`${styles.span} ${styles.bold} `}> {writerName}</span>
                <reactMoment className={`${styles.span} ${styles.gray} `}>{writeTime}</reactMoment>
                <span className={`${styles.span} ${styles.gray} `}> •  {writeTime}</span>
            </div>
        {/* 본문 */}
        <img id="ci" className={styles.photo} src={contentImgPath}></img>
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
        <ul className={styles.comment_ul}>
            <span className={`${styles.comment_span} ${styles.bold} `}>{commentUserNickname}</span> 
            <span className={`${styles.comment_span}`}>{commentContext}</span>
        </ul> 
        <button className={styles.tpBtn}>댓글 쓰기</button>       
        </div>
        
        </div>
    )

};

export default Post;