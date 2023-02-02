import {useState, React, useEffect} from 'react';
import styles from './Search.module.css'
const Search = () => {

    return (
        <div className={styles.container}>
            <input></input>
            <button>검색</button>
        </div>
    )
}

export default Search;