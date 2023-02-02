import {useState, React, useEffect} from 'react';
import styles from './Search.module.css'
import axios from 'axios';
const Search = () => {
    const acctoken = localStorage.getItem('accessToken');
    const [word, setWord] = useState('');

    const searchHandler = () => {
        const searchAPI = `/api/search` 
        axios.get(searchAPI,{params: {word: word}},{
            headers : {
                "Authorization" : acctoken,
                "Content-Type" : 'application/json'
            }
        })
        .then((res) => {
            console.log(res.data);
        })
        .catch((err) => {
            console.log(err);
        })
    }

    const wordHandler = (e) => {
        setWord(e.target.value)
    }
    return (
        <div className={styles.container}>
            <input onChange={(e) => wordHandler(e)}></input>
            <button onClick={searchHandler}>검색</button>
        </div>
    )
}

export default Search;