import styles from './Detail.module.css'
import React, {useState, useEffect, forwardRef, useImperativeHandle} from 'react'
import Modal from 'react-modal';
import { useSelector, useDispatch } from 'react-redux';
import axios from 'axios';
import { addPostobj, updatePostobj } from '../../redux/action';
import { IoMdClose } from 'react-icons/io'
import moment from "moment";

const Detail = forwardRef(({id}, detailRef) => {
    const dispatch = useDispatch();
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

    const [profileImgPath,setProfileImgPath] = useState('');
    const [commentList, setCommentList] = useState(null);
    const [commentText, setCommentText] = useState('');

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

    const postobjHandler = () => {
        if(postobj != undefined && postobj != null)
        {
            if(postobj['contentImgPath']){
                let tmp_path = postobj['contentImgPath'].replace(/\"/gi,"");
                let tmp_idx = tmp_path.indexOf("tmp");
                tmp_path = tmp_path.substring(tmp_idx);
                setContentImgPath(tmp_path);   
            }
                
            setWriterName(postobj['nickName']);
            setContent(postobj['content']);
            setCommentCount(postobj['commentCount']);
            setRegTime(postobj['regTime']);
            setPostId(postobj['id']);
            setLikeCount(postobj['likeCount']);
            setProfileImgPath(postobj['profileImgPath'].replace(/\"/gi,""));
            
            let commentinfo = postobj['boardCommentInfo']
            let infoctor = commentinfo?.constructor; // 댓글 객체 타입
            let likeinfo = postobj['boardLikeInfo']
            let likector = commentinfo?.constructor; // 좋아요 객체 타입

            console.log(commentinfo,postobj['id'])
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
        console.log(commentText);
        console.log(PostId);

            let writeAPI = `/api/${PostId}/comment`;
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
        let deleteAPI = `/api/boards/comments/${commentId}`;
            let acctoken = localStorage.getItem('accessToken');
            console.log(acctoken)
            axios.delete(deleteAPI, {
                headers : 
                    {'Authorization': acctoken}
            })
            .then((res) => {
                console.log(res.data)
            });
            dispatch(updatePostobj(postobj));
    }

    const commentListHandler = commentList?.map((data,idx) => <li className={styles.comment_li}key={idx}>
    <span className={`${styles.comment_span} ${styles.bold} `}>{data.commentUserNickname}</span> 
    <span className={`${styles.comment_span}`}> {data.commentContext}</span>
    <br/><span className={`${styles.gray}`}> {writetimeHandler(data.createdDate)}</span>
    <button className={styles.deleteBtn} onClick={() => commentDeleteHandler(data.commentId)}>삭제</button>
    </li>)


    return(
        <div>
            <Modal style={modalStyle} isOpen={ModalisOpen} onRequestClose={() => setModalisOpen(false)} ariaHideApp={false}>  

                <div className={styles.photo_container}>
                    <img className={styles.photo} src={contentImgPath}></img>
                </div>
                <div className={styles.content}>
                    <div className={styles.box}>
                        <img className={styles.profileImg} src={`${profileImgPath}`}/>
                    </div>
                    <span className={`${styles.span} ${styles.bold} `}> {writerName}</span>
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