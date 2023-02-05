import {useState, React, useEffect} from 'react';
import styles from './Feed.module.css'
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
        <div className={styles.feed_contents}>
            {postobj && postobj.map((obj,idx) => 
            <Post key={idx} id={obj.id}/>)}

        
        </div>
    )

};

export default Feed;