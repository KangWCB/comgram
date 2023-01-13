import axios from 'axios';
import { useDispatch } from 'react-redux';
import { addPostobj, updatePostobj, resetPostobj } from '../../redux/action';

async function GetPostobj(action) {
    let act = '';
    if(action = 'add')
    {
        console.log(`getpostobj add`)
        act = addPostobj;
    }
    else if (action = 'update')
    {
        console.log(`getpostobj update`)
        act = updatePostobj;
    }
    const dispatch = useDispatch();
    let acctoken = await localStorage.getItem('accessToken');
    const config = {"Content-Type" : 'application/json'};
    await axios.get('/api/boards/list', {headers : 
        {'Authorization': acctoken}},config)
    .then((res) => {
        
        let rd = res.data['data'];
        let rd_len = rd['length'];
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