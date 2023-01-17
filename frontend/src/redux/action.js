import {} from "./postobjReducer";
import axios from 'axios';

export const addPostobj = () => async dispatch => {
    let acctoken = localStorage.getItem('accessToken');
    console.log(acctoken);
    const config = {"Content-Type" : 'application/json'};
    const resdata = await axios.get('/api/boards/list', {headers : 
        {'Authorization': acctoken}},config)
    .then((res) => res.data);

    dispatch ({
        type: "OBJECT_ADD",
        payload: resdata['data']
    });
};

export const updatePostobj = (obj) => async dispatch => {
    console.log("update_act")
    let boardId = obj['id'];

    let api = `/api/boards/${boardId}`;
    console.log(api);
    let acctoken = localStorage.getItem('accessToken');
    const config = {"Content-Type" : 'application/json'};
    const resdata = await axios.get(api, {headers : 
        {'Authorization': acctoken}},config)
    .then((res) => res.data);
    
    dispatch ({
        type: "OBJECT_UPDATE",
        payload: resdata,
    })
    
}

export const resetPostobj = () => ({
    type: "OBJECT_RESET",
})

