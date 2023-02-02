import styles from './Info.module.css';
import React, {useState, useEffect, useRef, createRef } from 'react';
import Detail from '../components/Feed/Detail';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import wait from 'waait';

const Info = () => {
    const [refRender, setRefRender] = useState(false);
    const location = useLocation();
    const userid = localStorage.getItem('userId');
    const stateid = location.state?.id;
    const memberId = (stateid != null) ? stateid : userid;
    //디테일 페이지 모달핸들링
    const detailRef = useRef([]);

    const [profileImgPath, setProfileImgPath] = useState('');
    const [nickname, setNickname] = useState('');
    const [postcnt, setPostcnt] = useState(0); 
    const [boardList, setBoardList] = useState(null);
    const [followerCnt, setFollowerCnt] = useState(0);
    const [followingCnt, setFollowingCnt] = useState(0);
    const [isFollow, setIsFollow] = useState('');
    const [followBtnText, setFollowBtnText] = useState('팔로우');
    
    const fbtnId = document.getElementById('followBtn');

    useEffect(() => {
        console.log(memberId);
        infoobjHandler();
        boardsHandler();
        isFollowHandler();
        followCountHandler();

    }, [])

    useEffect(()=> {
        console.log(isFollow)
        if(isFollow == 'mine')
        {
            console.log("내꺼")
            fbtnId.style.display = 'none';
        }
        else if(isFollow == 'notFollow')
        {
            setFollowBtnText("팔로우");
            fbtnId.style.backgroundColor = '#50BCDF'
        }
        else if(isFollow == 'follow')
        {
            setFollowBtnText("언팔로우");
            fbtnId.style.backgroundColor = 'rgb(199, 197, 197)'
        }
        
    },[isFollow])

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
        console.log(acctoken)
        const getinfoAPI = `api/members/${memberId}/info`
        axios.get(getinfoAPI, {
            headers :
            {
                'Authorization' : acctoken
            }
        })
        .then((res) => {
            let tmp_path = res.data['profilePhotoUrl'].replace(/\"/gi,"");
            let tmp_idx = tmp_path.indexOf("tmp");
            tmp_path = tmp_path.substring(tmp_idx);
            setProfileImgPath(tmp_path);
            setNickname(res.data['nickname']);
        })
        .catch((err) => {
            console.log(err);
        });
    };

    const isFollowHandler = async () => {
        let acctoken = localStorage.getItem('accessToken');
        const isFollowAPI = `api/members/${memberId}/isFollow`
        await axios.get(isFollowAPI, {
            headers :
            {
                'Authorization' : acctoken
            }
        })
        .then((res) => {
            console.log(res.data['isFollow'])
            setIsFollow(res.data['isFollow'])
        })
        .catch((err) => {
            console.log(err);
        });
    };

    const followCountHandler = () => {
        let acctoken = localStorage.getItem('accessToken');
        const followingCountAPI = `api/members/${memberId}/followingCount`
        const followerCountAPI = `api/members/${memberId}/followerCount`

        axios.get(followingCountAPI, {
            headers :
            {
                'Authorization' : acctoken
            }
        })
        .then((res) => {
            setFollowingCnt(res.data['count']);
        })
        .catch((err) => {
            console.log(err, "followingcount 에러");
        });

        axios.get(followerCountAPI, {
            headers :
            {
                'Authorization' : acctoken
            }
        })
        .then((res) => {
            setFollowerCnt(res.data['count']);
        })
        .catch((err) => {
            console.log(err, "followercount 에러");
        });
    };

    const followHandler = async () => {
        let acctoken = localStorage.getItem('accessToken');
        const followAPI = `api/follow/${memberId}`
        await axios.post(followAPI, {}, {
            headers :
            {
                'Authorization' : acctoken
            }
        })
        .then((res) => {
            console.log(res);
            followCountHandler();
            isFollowHandler();
        })
        .catch((err) => {
            console.log(err);
        });
    };


    const boardsHandler = async () => {
        const getBoardsAPI = `api/members/${memberId}/boards`;
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
                    <button id='followBtn' className={`${styles.followBtn} ${styles.follow}`}onClick={() => followHandler()}>{followBtnText}</button>
                </div>
                <div>
                    <span className={styles.span}>게시글</span>
                    <span className={`${styles.num_span} ${styles.bold} `}>{postcnt}</span>
                    <span className={styles.span}>팔로워</span>
                    <span className={`${styles.num_span} ${styles.bold} `}>{followerCnt}</span>
                    <span className={styles.span}>팔로우</span>
                    <span className={`${styles.num_span} ${styles.bold} `}>{followingCnt}</span>
                </div>
            </div>
            <div className={styles.board_container}>
                {boardList && boardsView}
            </div>
            
        </div>
    )
};

export default Info;