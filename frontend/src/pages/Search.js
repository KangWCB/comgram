import {useState, React, useEffect, useRef, createRef} from 'react';
import styles from './Search.module.css'
import axios from 'axios';
import Detail from '../components/Feed/Detail';
import { BsSearch } from "react-icons/bs"

const Search = () => {
    const acctoken = localStorage.getItem('accessToken');
    const [refRender, setRefRender] = useState(false);
    const [word, setWord] = useState('');
    const [cond, setCond] = useState('');
    const [resultCnt, setResultCnt] = useState(0);
    const [searchResult, setSearchResult] = useState(null);
    const [resultFail, setResultFail] = useState(false);
    useEffect(() => {
        if(searchResult)
        {
            detailRef.current = searchResult?.map((_,idx) =>
                detailRef.current[idx] ?? createRef()
            );
            setRefRender(!refRender)
        }
    },[searchResult])
    const detailRef = useRef([]);
    // 게시글 boardId imgUrl
    // cond Member, Board 멤버면 mamberId nickName profileImgUrl

    const searchHandler = () => {
        if(word != '')
        {
            const searchAPI = `/api/search` 
            axios.get(searchAPI,{
                headers : {
                    "Authorization" : acctoken,
                    "Content-Type" : 'application/json'
                },
                params : {
                    "word" : word
                }
            })
            .then((res) => {
                console.log(res.data);
                setSearchResult(res.data['list']);
                setCond(res.data['cond']);
                setResultCnt(res.data['count'])
                if(res.data['count'] == 0)
                {
                    setResultFail(true);
                }
                else
                {
                    setResultFail(false);
                }
                console.log(res.data['cond'])
                if(res.data['cond'] == 'Board')

                    console.log("보드")
                if(res.data['cond'] == 'Member')
                    console.log("멤버")
            })
            .catch((err) => {
                console.log(err);
            })
        }

    }

    const wordHandler = (e) => {
        setWord(e.target.value)
    }

    const openModalHandler = (idx) => {
        console.log("hi")
        detailRef.current.map((dref, i) => (i === idx && dref.current.modalHandler(true)));
    }

    const keyHandler = (e) => {
        if(e.key === 'Enter') {
            searchHandler();
        }
    }

    const imgHandler = (url) => {
        if(url != null)
        {
            let tmp_path = url.replace(/\"/gi,"");
            let tmp_idx = tmp_path.indexOf("tmp");
            tmp_path = tmp_path.substring(tmp_idx);   
            return tmp_path;
        }
    }

    const boardsView = searchResult?.map((data, idx) => <li key={idx} style={{ listStyle: 'none', display:'inline-block', width:'33%'}}>

        <div className={styles.list_container}>
            <img onClick={() => openModalHandler(idx)} id={data?.boardId} className={styles.boardImg} src={imgHandler(data?.imgUrl)}></img>
            <Detail ref={detailRef.current[idx]} id={data?.boardId}/>
        </div>
        </li>)

    const membersView = searchResult?.map((data, idx) => <li key={idx} style={{ listStyle: 'none', width:'90%'}}>
        
        <div className={styles.member_container}>
            <div className={styles.box}>
                <img className={styles.profileImg} src={imgHandler(data?.profileImgUrl)}/>
            </div>
            <span className={styles.span_nickname}>{data.nickName}</span>
            
        </div>
    </li>)

    return (
        <div className={styles.container}>
            <div className={styles.search_container}>
                <input className={styles.searchInput} onKeyDown={(e) => keyHandler(e)} onChange={(e) => wordHandler(e)}></input>
                <BsSearch onClick={searchHandler} className={styles.searchIcon}/>
            </div>
            <div className={styles.result_container}>
                <span style={(resultCnt != 0)?{display: ""} : {display: "none"}}className={styles.span_success}>검색 결과 총 {resultCnt}개</span>
            </div>

            
            <div className={styles.board_container}>
                {cond == 'Member' && membersView}
                {cond == 'Board' && boardsView}
                <span style={resultFail?{display: ""} : {display: "none"}}className={styles.span_fail}>아쉽지만 검색결과가 없네요..</span>
            </div>
            
            
        </div>
    )
}

export default Search;