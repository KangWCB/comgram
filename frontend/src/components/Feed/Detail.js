import styles from './Detail.module.css'
import React, {useState, useEffect} from 'react'
import Modal from 'react-modal';
import { useSelector, useDispatch } from 'react-redux';
import { addPostobj, updatePostobj } from '../../redux/action';


const Detail = (id, isopen) => {
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
    
    useEffect(() => {
        setModalisOpen(isopen);
    }, [])
    useEffect(() => {
        let data = selectorData;
        if(data)
        {
            let objidx = data.findIndex(obj => obj.id === id['id'])
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
            let infoctor = commentinfo.constructor; // 댓글 객체 타입
            let likeinfo = postobj['boardLikeInfo']
            let likector = commentinfo.constructor; // 좋아요 객체 타입
            if(commentinfo)
            {
                if (infoctor === Array)
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

    useEffect(() => {
        if(ModalisOpen)
        {
            
        }
    }, [ModalisOpen])

    const modalStyle = {
        overlay: {
            zIndex: 5,
            backgroundColor: 'rgba(0,0,0,0.25)'
        },
        content: {padding: '0px'},    
    }

    const modalHandler = () => {
        setModalisOpen(true);
    }

    return(
        <div>
            <button onClick={modalHandler}>open</button>
            <button onClick={() => setModalisOpen(false)}>close</button>
            <Modal style={modalStyle} isOpen={ModalisOpen} onRequestClose={() => setModalisOpen(false)}>  

                <div className={styles.photo_container}>
                    <img className={styles.photo} src={contentImgPath}></img>
                </div>
                <div className={styles.content}>
                    <div className={styles.box}>
                        <img className={styles.profileImg} src={`${profileImgPath}`}/>
                    </div>
                    <span className={`${styles.span} ${styles.bold} `}> {writerName}</span>
                    <div className={styles.comment}>
                        hi
                    </div>
                </div>
                
            </Modal>
        </div>    
    )
}
export default Detail;