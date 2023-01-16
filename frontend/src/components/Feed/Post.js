import axios from "axios";
import {useState, React, useEffect} from 'react';
import styles from './Feed.module.css'
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";
import moment from "moment";
import { addPostobj, updatePostobj } from '../../redux/action';
import { useSelector, useDispatch } from 'react-redux';
import getPostobj from './GetPostobj';

const Post = (id) => {
    const acctoken = localStorage.getItem('accessToken');
    
    const [like, setLike] = useState(false);
    const [ismoreView, setIsmoreView] = useState(false);
    const [viewMoreText, setViewMoreText] = useState("... 더 보기");
    const [originContent,setOriginContent] = useState('');
    // postobj
    const [writerName, setWriterName] = useState("");
    const [content, setContent] = useState('')
    const [contentImgPath, setContentImgPath] = useState(null);

    const [PostId, setPostId] = useState(0);
    const [likeUserNickName, setLikeUserNickName] = useState('hi');
    const [commentCount, setCommentCount] = useState(0);
    const [regTime, setRegTime] = useState(null);
    const [likeCount,setLikeCount] = useState(0);
    const [commentContext,setCommentContext] = useState("");
    const [commentUserNickname, setCommentUserNickname] = useState("");
    const [profileImgPath,setProfileImgPath] = useState('');
    const [writeTime,setWriteTime] = useState('');
    let diffTime = {         
        day:'',
        hour:'',
        min:'',
        sec:'',
    };    
    let origin_content = "";
    let cut_content = "";
    
    const selectorData = useSelector(state => state.postobjReducer);
    const [data, setData] = useState(selectorData);
    let postobj = null;
    
    useEffect(() => {
      setData(selectorData);
      if(data)
      {
        let objidx = data['postobj'].findIndex(obj => obj.id === id['id'])
        postobj = data['postobj'][objidx];
    }

    }, [selectorData])
    
    useEffect(() => {
        writetimeHandler(regTime);
    },[regTime]);

    useEffect(() => {
        console.log('postobj 변경')
        console.log(postobj);
        
        if(postobj.length!= 0)
        {
            if(postobj['contentImgPath']){
                let tmp_path = postobj['contentImgPath'].replace(/\"/gi,"");
                let tmp_idx = tmp_path.indexOf("tmp");
                tmp_path = tmp_path.substring(tmp_idx);
                console.log(tmp_path);
                setContentImgPath(tmp_path);   
            }
                
            setWriterName(postobj['nickName']);
            setContent(postobj['content']);
            setCommentCount(postobj['commentCount']);
            setLike(postobj['pushLike']);
            setRegTime(postobj['regTime']);
            setPostId(postobj['id']);
            setLikeCount(postobj['likeCount']);
            setProfileImgPath(postobj['profileImgPath'].replace(/\"/gi,""));
            if(postobj['boardMainCommentInfo'])
            {
                setCommentContext(postobj['boardMainCommentInfo']['commentContext']);
                setCommentUserNickname(postobj['boardMainCommentInfo']['commentUserNickname']);
            }
            if(postobj['boardMainLikeInfo'])
            {
                setLikeUserNickName(postobj['boardMainLikeInfo']['likeUserNickName']);
            }
        }
        else
            console.log("obj없음");
        },[postobj]);


    const likeHandler = () => {
        console.log("like 호출");
        let likeAPI = `/api/boards/${PostId}/like`;

        let acctoken = localStorage.getItem('accessToken');
        console.log(acctoken);

        axios.post(likeAPI, {}, {
            headers : 
                {'Authorization': acctoken}
        })
        .then((res) => {
            console.log(res.data);
        });
        
        getPostobj('update',PostId);
    };

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
                <span className={`${styles.span} ${styles.gray} `}> •  {writeTime}</span>
            </div>
        {/* 본문 */}
        <img id="ci" /*onError={imgHandler}*/ className={styles.photo} src={contentImgPath}></img>
        <div className={styles.icon_form}>
            <div className={styles.likeIcon} onClick={likeHandler}>
            {
                like ? (<BsHeartFill className={styles.likeIcon}/>) : (<BsHeart className={styles.likeIcon}/>)
            }
            </div>
            <SlSpeech className={styles.icon}/>
        </div>
        <div className={styles.comment_form}>
            <span className={`${styles.span} ${styles.bold} `}>{likeUserNickName}</span><span className={styles.span}>님 </span><span className={`${styles.span} ${styles.bold}`}>외 {likeCount-1}명</span><span className={styles.span}>이 좋아합니다</span>
        </div>
   
        <div className={styles.content}>
            <span className={styles.span}>{content}</span>
            <button className={styles.tpBtn} onClick={viewMoreHandler}>{viewMoreText}</button>
        </div>

        <div className={styles.content_comment}>
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