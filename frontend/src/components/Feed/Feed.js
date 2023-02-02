import {useState, React, useEffect} from 'react';
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";
import styles from './Feed.module.css'
import axios from 'axios';
import Post from './Post';


import { addPostobj, resetPostobj, updatePostobj} from '../../redux/action';
import { useSelector, useDispatch } from 'react-redux';

const Feed = () => {

    const [postobj, setPostobj] = useState([]);
    const dispatch = useDispatch();
    const selectorData = useSelector(state => state.postobjReducer);
    const [data, setData] = useState(selectorData);
    
    useEffect(() => {
      setData(selectorData)
      setPostobj(data.postobj);
    }, [selectorData])

    useEffect(() => {
        dispatch(updatePostobj(postobj));
    },[postobj])

    useEffect(() => {
        dispatch(addPostobj());    
    },[]);


    const resetobj = () => {
        dispatch(resetPostobj());
    }

    

    return (
        <div className={styles.contents}>
            <button onClick={resetobj}>RESET POSTOBJ</button>
            {postobj && postobj.map((obj,idx) => 
            <Post key={idx} id={obj.id}/>)}

        
        </div>
    )

};

export default Feed;