import {useState, React, useEffect} from 'react';
import { BsHeart, BsHeartFill } from "react-icons/bs";
import { SlSpeech } from "react-icons/sl";
import styles from './Feed.module.css'
import axios from 'axios';
import Post from './Post';

const Feed = (inherit_token) => {
    const [postobj, setPostobj] = useState([]);
    let obj_table = {
        boardMainCommentInfo : [],
        boardMainLikeInfo : [],
        id: 'id',
        content: 'content',
        contentImgPath: 'contentImgPath',
        commentCount: 'commentCount',
        likeCount: 'likeCount',
        regTime: 'null',
        profileImgPath : 'profileImgPath',
        nickName : 'nickname',
    };


    async function GetPostobj() {
        let acctoken = await localStorage.getItem('accessToken');
        let obj_arr = [];
        console.log(acctoken);
        const config = {"21wContent-Type" : 'application/json'};
        await axios.get('/api/boards/list', {headers : 
            {'Authorization': acctoken}},config)
        .then((res) => {
            
            let rd = res.data['data'];
            let rd_len = rd['length'];
            
            for(var i=0;i<rd_len;i++)
            {
                obj_table = {
                    boardMainCommentInfo : rd[i]['boardMainCommentInfo'],
                    boardMainLikeInfo : rd[i]['boardMainLikeInfo'],
                    id: rd[i]['id'],
                    content: rd[i]['content'],
                    contentImgPath: rd[i]['contentImgPath'],
                    commentCount: rd[i]['commentCount'],
                    likeCount: rd[i]['likeCount'],
                    regTime: rd[i]['regTime'],
                    profileImgPath : rd[i]['profileImgPath'],
                    nickName: rd[i]['nickName'],
                }
                obj_arr = obj_arr.concat(obj_table);
                
            }
            console.log(obj_arr);
            setPostobj(obj_arr);
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

                {postobj.map((obj) => 
                <Post postobj={obj}/>)}

        
        </div>
    )

};

export default Feed;