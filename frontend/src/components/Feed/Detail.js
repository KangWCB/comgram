import styles from './Detail.module.css'
import React, {useState, useEffect, forwardRef, useImperativeHandle} from 'react'
import Modal from 'react-modal';
import { useSelector, useDispatch } from 'react-redux';
import axios from 'axios';
import { updatePostobj } from '../../redux/action';
import { IoMdClose } from 'react-icons/io'
import moment from "moment";
import { useNavigate } from 'react-router-dom';


const Detail = forwardRef(({id}, detailRef) => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [ModalisOpen, setModalisOpen] = useState(false);
    const selectorData = useSelector(state => state.postobjReducer.postobj);
    const [postobj, setPostobj] = useState(null);
    //postobj
    const [writerName, setWriterName] = useState("");
    const [content, setContent] = useState('')
    const [contentImgPath, setContentImgPath] = useState(null);
    const [PostId, setPostId] = useState(0);
    const [likeUserNickName, setLikeUserNickName] = useState('');
    const [likeUserProfilePath, setLikeUserProfilePath] = useState(null);
    const [commentCount, setCommentCount] = useState(0);
    const [regTime, setRegTime] = useState(null);
    const [likeCount,setLikeCount] = useState(0);
    const [writerId, setWriterId] = useState(null);
    const [myPost, setMyPost] = useState(false);
    const [profileImgPath,setProfileImgPath] = useState('');
    const [commentList, setCommentList] = useState(null);
    const [commentText, setCommentText] = useState('');

    const userid = localStorage.getItem('userId')

    useImperativeHandle(detailRef, () => ({
        modalHandler: (bool) => {
            setModalisOpen(bool);
        }
    }))

    useEffect(() => {
        if(postobj != null)
            dispatch(updatePostobj(postobj));
    },[])


    

    useEffect(() => {
        let data = selectorData;

        if(data)
        {
            let objidx = data.findIndex(obj => obj.id === id)
            setPostobj(data[objidx]);
        }
    },[selectorData])
    
    useEffect(() => {
        postobjHandler()
    },[postobj])

    useEffect(() => {
        if(writerId == userid)
            setMyPost(true);
    },[writerId])

    const postobjHandler = () => {
        if(postobj != undefined && postobj != null)
        {
            if(postobj['contentImgPath']){
                setContentImgPath(imgHandler(postobj['contentImgPath']));    
            }
                
            setWriterId(postobj['writerId']);
            setWriterName(postobj['nickName']);
            setContent(postobj['content']);
            setCommentCount(postobj['commentCount']);
            setRegTime(postobj['regTime']);
            setPostId(postobj['id']);
            setLikeCount(postobj['likeCount']);
            setProfileImgPath(imgHandler(postobj['profileImgPath']));

           
            let commentinfo = postobj['boardCommentInfo']
            let infoctor = commentinfo?.constructor; // 댓글 객체 타입
            let likeinfo = postobj['boardLikeInfo']
            let likector = commentinfo?.constructor; // 좋아요 객체 타입

            if(commentinfo)
            {
                if (infoctor === Array)
                {
                    setCommentList(commentinfo)
                }
                else
                {
                    dispatch(updatePostobj(postobj));
                }
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

    const imgHandler = (url) => {
        let path = url?.replace(/\"/gi,"");
        let check = path.substring(1,5);
        if(check == 'home')
        {
            path = 'imagePath' + path; 
        }
        return path;
    }

    const modalStyle = {
        overlay: {
            zIndex: 5,
            backgroundColor: 'rgba(0,0,0,0.25)'
        },
        content: {padding: '0px'},    
    }

    const keyHandler = (e) => {
        if(e.key === 'Enter') {
            commentWriteHandler();
        }
    }

    const writetimeHandler = (regTime) => {

        let rgTime = moment(regTime).format();
        let ntMoment = moment();
        let rgMoment = moment(rgTime);

        let diffTime = {
            day: Math.floor(moment.duration(ntMoment.diff(rgMoment)).asDays()),
            hour: Math.floor(moment.duration(ntMoment.diff(rgMoment)).asHours()),
            min: Math.floor(moment.duration(ntMoment.diff(rgMoment)).asMinutes()),
            sec: Math.floor(moment.duration(ntMoment.diff(rgMoment)).asMilliseconds()/1000)
        };

        let timeText = '';

        for (const key in diffTime){
            let tmpTime = diffTime[key];
            if(tmpTime)
            {
                if(key == 'day')
                    timeText = `${tmpTime}일 전`
                else if(key == 'hour')
                    timeText = `${tmpTime}시간 전`
                else if(key == 'min')
                    timeText = `${tmpTime}분 전`
                else if(key == 'sec')
                    timeText = `${tmpTime}초 전`
                break;
            }
        }
        return timeText;

    }

    const commentWriteHandler = () => {
        let writeAPI = `${process.env.REACT_APP_BACKEND}` + `/api/${PostId}/comment`;
        let acctoken = localStorage.getItem('accessToken');
        const config = {"Content-Type" : 'application/json'};
        const obj = {
            "comment" : commentText
        }
        axios.post(writeAPI, obj, {
            headers : 
                {
                'Authorization': acctoken,
                'Content-Type' : 'application/json'
                }
        })
        .then((res) => {
            console.log(res.data)
            setCommentText('');
        })
        .catch((err) => {console.log(err)});
        dispatch(updatePostobj(postobj));
    
    }

    const commentDeleteHandler = (commentId) => {
        console.log(commentId)
        let commentdeleteAPI = `${process.env.REACT_APP_BACKEND}` + `/api/comments/${commentId}`;
        let acctoken = localStorage.getItem('accessToken');
        console.log(acctoken)
        axios.delete(commentdeleteAPI, {
            headers : 
                {'Authorization': acctoken}
        })
        .then((res) => {
            console.log(res.data)
        });
        dispatch(updatePostobj(postobj));
    }

    const postDeleteHandler = () => {
        if(window.confirm("정말 삭제하시겠습니까?"))
        {
            let postdeleteAPI = `${process.env.REACT_APP_BACKEND}` + `/api/boards/${PostId}`
            let acctoken = localStorage.getItem('accessToken');
            axios.delete(postdeleteAPI, {
                headers :
                {'Authorization': acctoken}
            })
            .then((res) => {
                console.log(res.data)
            });
            setModalisOpen(false);
            window.location.href="/";
        }

    }

    const profileClickHandler = () => {
        setModalisOpen(false);
        navigate("/info", {state: {id: writerId}});
    }

    const commentListHandler = commentList?.map((data,idx) => <li className={styles.comment_li}key={idx}>
    <span className={`${styles.comment_span} ${styles.bold} `}>{data.commentUserNickname}</span> 
    <span className={`${styles.comment_span}`}> {data.commentContext}</span>
    <br/><span className={`${styles.gray}`}> {writetimeHandler(data.createdDate)}</span>
    {(data.commentUserId == userid) && <button className={styles.deleteBtn} onClick={() => commentDeleteHandler(data.commentId)}>삭제</button>}
    </li>)


    return(
        <div>
            <Modal style={modalStyle} isOpen={ModalisOpen} onRequestClose={() => setModalisOpen(false)} ariaHideApp={false}>  

                <div className={styles.photo_container}>
                    <img className={styles.photo} src={contentImgPath}></img>
                </div>
                <div className={styles.content}>
                    <div className={styles.box}>
                        <img onClick={profileClickHandler} className={styles.profileImg} src={`${profileImgPath}`}/>
                    </div>
                    <span className={`${styles.span} ${styles.bold} `}> {writerName}</span>
                    {myPost && <button onClick={() => postDeleteHandler()}className={styles.deleteBtn}>글 삭제</button>}
                    <IoMdClose className={styles.closeBtn}onClick={() => setModalisOpen(false)}/>
                    <div className={styles.comment}>
                        <div className={styles.box}>
                            <img className={styles.profileImg} src={`${profileImgPath}`}/>
                        </div>
                        <span className={`${styles.span} ${styles.bold} `}> {writerName}</span>
                        <span className={styles.span}>{content}</span>
                        {commentList && commentListHandler}
                    </div>
                    <div className={styles.comment_container}>
                        <input className={styles.comment_input} onKeyPress={keyHandler} onChange={(e) => setCommentText(e.target.value)} value={commentText} placeholder='댓글 쓰기'></input>
                        <button className={styles.comment_btn} disabled={commentText === '' ? true : false}onClick={commentWriteHandler}>게시</button>
                    </div>
                </div>
                
                
            </Modal>
        </div>    
    )
})
export default Detail;