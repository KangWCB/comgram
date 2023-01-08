import {useState, React, useEffect} from 'react';
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";
import styles from './Feed.module.css'
import axios from 'axios';
import Post from './Post';

const Feed = (inherit_token) => {

    let rd = '';
    const [postobj, setPostobj] = useState([]);

    async function GetPostobj() {
        let acctoken = await localStorage.getItem('accessToken');
        
        await axios.get('/api/boards/list', {headers : 
            {'Authorization': acctoken}})
        .then((res) => {
            
            rd = res.data['data'];
            console.log(rd[0]);
            let rd_len = rd['length'];
            /*
            let tmp_postobj = [{
                id: rd[i]['id'],
                content: rd[i]['content'],
                contentImgPath: rd[i]['contentImgPath'],
                commentCount: rd[i]['commentCount'],
                likeCount: rd[i]['commentCount'],
                regTime: rd[i]['regTime'],
                profileImgPath : rd[i]['profileImgPath'],
            }]
            for(var i=0;i<rd_len;i++)
            {
                setPostobj(rd[i], ...postobj);
            }
            */
            setPostobj(rd[0]);
            console.log(postobj);
            

            

            
        })
        .catch((err) => {
            console.log(err);
        });
    };

    useEffect(() => {
        GetPostobj();    

    },[]);


    

    

    return (
        <div className={styles.contents}>
            <button style={{float:'left'}} onClick = {() => GetPostobj()}>test</button>
            <Post postobj={postobj}/>
        
        </div>
    )

};

export default Feed;