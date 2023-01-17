import axios from 'axios';
import { useDispatch } from 'react-redux';
import { addPostobj, updatePostobj, resetPostobj } from '../../redux/action';

function GetPostobj()  {
    let action = 'add';
    let act = '';
    let api = '';
    const dispatch = useDispatch();
    if(action = 'add')
    {
        console.log(`getpostobj add`)
        act = addPostobj;
        api = '/api/boards/list';
    }
    else if (action = 'update')
    {
        console.log(`getpostobj update`)
        act = updatePostobj;
        //api = `api/boards/${id}`
    }
    let acctoken = localStorage.getItem('accessToken');
    console.log(acctoken);
    const config = {"Content-Type" : 'application/json'};
    axios.get(api, {headers : 
        {'Authorization': acctoken}},config)
    .then((res) => {
        
        let rd = res.data['data'];
        let rd_len = rd['length'];
        console.log(rd[0]);
        for(var i=0;i<rd_len;i++)
        {
            let obj_table = {
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
                pushLike: rd[i]['pushLike'],
            }
            dispatch(act(obj_table));     
        }
    })
    .catch((err) => {
        console.log(err);
    });
};

export default GetPostobj;