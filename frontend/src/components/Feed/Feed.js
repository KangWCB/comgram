import {useState, React, useEffect} from 'react';
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";
import styles from './Feed.module.css'
import axios from 'axios';
import Post from './Post';

import { resetPostobj } from '../../redux/action';
import { useSelector, useDispatch } from 'react-redux';
import getPostobj from './GetPostobj';
const Feed = (inherit_token) => {

    const [postobj, setPostobj] = useState([]);
    const dispatch = useDispatch();
    const selectorData = useSelector(state => state.postobjReducer);
    const [data, setData] = useState(selectorData);
    
    useEffect(() => {
      setData(selectorData)
      console.log("redux 갱신")
      setPostobj(data.postobj);
      console.log(postobj)
    }, [selectorData])



    useEffect(() => {
        dispatch(getPostobj('add'));    
    },[]);


    const resetobj = () => {
        dispatch(resetPostobj());
    }

    

    return (
        <div className={styles.contents}>
            <button onClick={resetobj}>RESET POSTOBJ</button>
                {postobj.map((obj) => 
                <Post id={obj.id}/>)}

        
        </div>
    )

};

export default Feed;