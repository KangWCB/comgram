import axios from "axios";
import {useState, React, useEffect, useRef} from 'react';
import styles from './Feed.module.css'
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";
import moment from "moment";
import { addPostobj, updatePostobj } from '../../redux/action';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from "react-router-dom";
import Detail from './Detail';

const Post = (id) => {
    const acctoken = localStorage.getItem('accessToken');
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [like, setLike] = useState(false);
    const [ismoreView, setIsmoreView] = useState(false);
    const [moreBtnVisible, setMoreBtnVisible ] = useState(false);
    const [viewMoreText, setViewMoreText] = useState("... 더 보기");
    const [originContent,setOriginContent] = useState('');
    const [likecntVisible, setLikecntVisible] = useState(false);
    // postobj
    const [writerName, setWriterName] = useState("");
    const [content, setContent] = useState('')
    const [contentImgPath, setContentImgPath] = useState(null);
    const [PostId, setPostId] = useState(0);
    const [likeUserNickName, setLikeUserNickName] = useState('');
    const [likeUserProfilePath, setLikeUserProfilePath] = useState(null);
    const [commentCount, setCommentCount] = useState(0);
    const [regTime, setRegTime] = useState(null);
    const [likeCount,setLikeCount] = useState(0);
    const [commentContext,setCommentContext] = useState("");
    const [commentUserNickname, setCommentUserNickname] = useState("");
    const [profileImgPath,setProfileImgPath] = useState('');
    const [writeTime,setWriteTime] = useState('');
    const [writerId, setWriterId] = useState(null);

    let diffTime = {         
        day:'',
        hour:'',
        min:'',
        sec:'',
    };    
    let origin_content = "";
    let cut_content = "";
    let first_like = false;
    let content_limit = 50;
    
    const selectorData = useSelector(state => state.postobjReducer.postobj);
    const [postobj, setPostobj] = useState(null);
    //디테일 페이지 모달핸들링
    const detailRef = useRef();

    useEffect(() => {
        dispatch(addPostobj())
    },[])
    
    useEffect(() => {
        let data = selectorData;
        
        if(data)
        {
            let objidx = data.findIndex(obj => obj.id === id['id'])
            setPostobj(data[objidx]);
        }
        

    }, [selectorData])
    
    useEffect(() => {
        writetimeHandler(regTime);
    },[regTime]);

    useEffect(() => {
        if(likeCount>0)
            setLikecntVisible(true);
        else
            setLikecntVisible(false);
    },[likeCount])

    useEffect(() => {
        postobjHandler()
        if(content.length > content_limit)
        {
            setMoreBtnVisible(true);
            contentHandler();
        }
    },[postobj]);

    const postobjHandler = () => {
        if(postobj != undefined && postobj != null)
        {
            if(postobj['contentImgPath']){
                setContentImgPath(imgHandler(postobj['contentImgPath']));   
            }
         
            setWriterName(postobj['nickName']);
            setWriterId(postobj['writerId']);
            setContent(postobj['content']);
            setCommentCount(postobj['commentCount']);
            setRegTime(postobj['regTime']);
            setPostId(postobj['id']);
            setLikeCount(postobj['likeCount']);

            setProfileImgPath(imgHandler(postobj['profileImgPath']));

            if(first_like)
            {
                first_like = true;
                setLike(postobj['pushLike']);
            }

            let commentinfo = postobj['boardCommentInfo']
            let infoctor = commentinfo?.constructor; // 댓글 객체 타입
            let likeinfo = postobj['boardLikeInfo']
            let likector = commentinfo?.constructor; // 좋아요 객체 타입
            if(commentinfo)
            {
                if (infoctor === Array && postobj['boardCommentInfo'].length != 0)
                {
                    setCommentContext(postobj['boardCommentInfo'][0]['commentContext']);
                    setCommentUserNickname(postobj['boardCommentInfo'][0]['commentUserNickname']);
                }
                else
                {
                    setCommentContext(postobj['boardCommentInfo']['commentContext']);
                    setCommentUserNickname(postobj['boardCommentInfo']['commentUserNickname']);
                }
            }
            else
            {
                setCommentUserNickname('');
            }
            if(likeinfo)
            {
                if (likector === Array)
                {
                    setLikeUserNickName(postobj['boardLikeInfo'][0]['likeUserNickName']);
                }
                else
                    setLikeUserNickName(postobj['boardLikeInfo']['likeUserNickName']);
            }
        }
        /*
        else
            dispatch(addPostobj());
        */
    };
    const likeHandler = () => {
        let likeAPI = `${process.env.REACT_APP_BACKEND}` + `/api/boards/${PostId}/like`;

        let acctoken = localStorage.getItem('accessToken');

        axios.post(likeAPI, {}, {
            headers : 
                {'Authorization': acctoken}
        })
        .then((res) => res.data);
        dispatch(updatePostobj(postobj));
        setLike(!like);

    };

    const imgHandler = (url) => {
        let path = url?.replace(/\"/gi,"");
        let check = path.substring(1,5);
        if(check == 'home')
        {
            path = 'imagePath' + path; 
        }
        return path;
    }


    const writetimeHandler = (regTime) => {

        let rgTime = moment(regTime).format();
        let ntMoment = moment();
        let rgMoment = moment(rgTime);

        diffTime = {
            day: Math.floor(moment.duration(ntMoment.diff(rgMoment)).asDays()),
            hour: Math.floor(moment.duration(ntMoment.diff(rgMoment)).asHours()),
            min: Math.floor(moment.duration(ntMoment.diff(rgMoment)).asMinutes()),
            sec: Math.floor(moment.duration(ntMoment.diff(rgMoment)).asMilliseconds()/1000)
        };

        for (const key in diffTime){
            let tmpTime = diffTime[key];
            if(tmpTime)
            {
                if(key == 'day')
                    setWriteTime(`${tmpTime}일 전`)
                else if(key == 'hour')
                    setWriteTime(`${tmpTime}시간 전`)
                else if(key == 'min')
                    setWriteTime(`${tmpTime}분 전`)
                else if(key == 'sec')
                    setWriteTime(`${tmpTime}초 전`)
                break;
            }
        }

    }



    useEffect(() => {
        contentHandler();
    },[ismoreView]);



    const contentHandler = () => {
        if(content.length > content_limit)
        {
            if(ismoreView == false)// ismoreview가 false면
            {
                console.log("짧게")
                cut_content = content.slice(0,content_limit);
                console.log(cut_content);
                setOriginContent(content);
                setContent(cut_content);
            }
        }
        else if(ismoreView == true)
        {
                console.log("길게")
                setContent(originContent);
        }
        
    }

    const viewMoreHandler = () => {
        if(ismoreView == false)
        {
            setIsmoreView(true);
            setViewMoreText("접기");
        }
        else if (ismoreView == true)
        {
            setIsmoreView(false);
            setViewMoreText("... 더 보기");
            
        }
        console.log(ismoreView,"more")
    };


    const openModalHandler = () => {
        detailRef.current?.modalHandler(true);
    }

    const profileClickHandler = () => {
        navigate("/info", {state: {id: writerId}});
    }


    return(
        <div>
        {/* 피드 */}
            <div className={styles.profile_form}>
                <div className={styles.box}>
                    <img onClick={profileClickHandler} className={styles.profileImg} src={`${profileImgPath}`}/>
                </div>
                <span className={`${styles.span} ${styles.bold} `}> {writerName}</span>
                <span className={`${styles.span} ${styles.gray} `}> •  {writeTime}</span>
            </div>
        {/* 본문 */}
        <div onClick={openModalHandler} className={styles.img_container}>
            <img id="contentImg" className={styles.photo} src={contentImgPath}></img>
        </div>
        <div className={styles.icon_form}>
            <div className={styles.likeIcon} onClick={likeHandler}>
            {
                like ? (<BsHeartFill className={styles.likeIcon}/>) : (<BsHeart className={styles.likeIcon}/>)
            }
            </div>
            <SlSpeech className={styles.icon} onClick={openModalHandler}/>
        </div>
        <div className={styles.comment_form}>
            {likecntVisible && <span className={`${styles.span} ${styles.bold} `}>{likeUserNickName}</span>}
            {likecntVisible && <span className={styles.span}>님</span>}
            {(likecntVisible && likeCount> 1) && <span className={`${styles.span} ${styles.bold}`}> 외 {likeCount-1}명</span>}
            {likecntVisible && <span className={styles.span}>이 좋아합니다</span>}
        </div>
   
        <div className={styles.content}>
            <span className={styles.span}>{content}</span>
            {moreBtnVisible && <button className={styles.tpBtn} onClick={() => viewMoreHandler()}>{viewMoreText}</button>}
        </div>

        <div className={styles.content_comment}>
        {(commentCount>1) && <button onClick={openModalHandler} className={styles.tpBtn}>댓글 {commentCount}개 모두 보기</button>}
        {(commentUserNickname && commentContext) ? <ul className={styles.comment_ul}>
            <span className={`${styles.comment_span} ${styles.bold} `}>{commentUserNickname}</span> 
            <span className={`${styles.comment_span}`}>{commentContext}</span>
        </ul> : <br/>}
        <button onClick={openModalHandler} className={styles.tpBtn}>댓글 쓰기</button>       
        </div>
            <Detail ref={detailRef} id= {id['id']}/>
        </div>
    )

};

export default Post;