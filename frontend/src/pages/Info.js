import styles from './Info.module.css'
import React, {useState, useEffect, useRef, createRef} from 'react'
import axios from 'axios'
import Detail from '../components/Feed/Detail';

const Info = (memberId) => {
    const [profileImgPath, setProfileImgPath] = useState('');
    const [nickname, setNickname] = useState('');
    const [postcnt, setPostcnt] = useState(0); 
    const [boardList, setBoardList] = useState(null);
    const [refRender, setRefRender] = useState(false);
    //디테일 페이지 모달핸들링
    const detailRef = useRef([]);

    useEffect(() => {
        infoobjHandler();
        boardsHandler();

    }, [])


    useEffect(() => {
        if(boardList)
        {
            detailRef.current = boardList?.map((_,idx) =>
                detailRef.current[idx] ?? createRef()
            );
            setRefRender(!refRender)
        }
    },[boardList])

    

    const infoobjHandler = () => {
        let acctoken = localStorage.getItem('accessToken');
        const getinfoAPI = 'api/members/info'
        axios.get(getinfoAPI, {
            headers :
            {
                'Authorization' : acctoken
            }
        })
        .then((res) => {
            setProfileImgPath(res.data['profilePhotoUrl'].replace(/\"/gi,""));
            setNickname(res.data['nickname']);
        })
        .catch((err) => {
            console.log(err);
        });
    };

    const followHandler = () => {

    };

    const boardsHandler = async () => {
        let id = 2;
        const getBoardsAPI = `api/members/${id}/boards`;
        await axios.get(getBoardsAPI)
        .then((res) => {
            setPostcnt(res.data['count'])
            setBoardList(res.data['data'])
        })
        .catch((err) => {
            console.log(err)
        })
    };

    const imgHandler = (url) => {
        let tmp_path = url.replace(/\"/gi,"");
        let tmp_idx = tmp_path.indexOf("tmp");
        tmp_path = tmp_path.substring(tmp_idx);   
        return tmp_path;
    }

    const openModalHandler = (idx) => {
        detailRef.current.map((dref, i) => (i === idx && dref.current.modalHandler(true)));
    }
    
    const boardsView = boardList?.map((data, idx) => <li style={{ listStyle: 'none', display:'inline-block', width:'33%'}}>
        <span>{data['id']}</span>
        <img onClick={() => openModalHandler(idx)} id={data['id']} className={styles.boardImg} src={imgHandler(data['photoUrl'])}></img>
        <Detail ref={detailRef.current[idx]} id={data['id']}/>
        </li>)

    return (
        <div className={styles.profile_container}>
            <div className={styles.box}>
                <img className={styles.profileImg} src={profileImgPath}/>
            </div>
            
            <div className={styles.info_container}>
                <div className={styles.nickname_container}>
                    <span className={styles.nickname_span}>{nickname}</span>
                    <button className={styles.followBtn}onClick={() => followHandler()}>팔로우</button>
                </div>
                <div>
                    <span className={styles.span}>게시글</span>
                    <span className={`${styles.num_span} ${styles.bold} `}>{postcnt}</span>
                    <span className={styles.span}>팔로워</span>
                    <span className={`${styles.num_span} ${styles.bold} `}>0</span>
                    <span className={styles.span}>팔로우</span>
                    <span className={`${styles.num_span} ${styles.bold} `}>0</span>
                </div>
            </div>
            <div className={styles.board_container}>
                {boardList && boardsView}
            </div>
            
        </div>
    )
};

export default Info;