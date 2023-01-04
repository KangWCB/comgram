import {useState, React, useEffect} from 'react';
import { BsHeart, BsHeartFill } from "react-icons/bs";
import styles from './Feed.module.css'

const Feed = () => {
    const [like, setLike] = useState(false);

    const likeHandler = () => {
        setLike(!like);
    };

    return (
        <div className={styles.content}>

        <img className={styles.photo} src="img/hi.png"></img>

        <div className={styles.comment}>
            <div className='like' onClick={likeHandler}>
            {
                like ? (<BsHeart className={styles.likeIcon}/>) : (<BsHeartFill className={styles.likeIcon}/>)
            }
            <h3>ㅋㅋ</h3>
        </div>
        </div>
    </div>
    )

};

export default Feed;