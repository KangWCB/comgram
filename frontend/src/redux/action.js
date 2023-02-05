import {} from "./postobjReducer";
import axios from 'axios';
import wait from 'waait';


export const addPostobj = () => async dispatch => {
    let acctoken = localStorage.getItem('accessToken');
    const config = {"Content-Type" : 'application/json'};
    let listAPI = `${process.env.REACT_APP_BACKEND}` + '/api/boards/list'
    const resdata = await axios.get(listAPI, {headers : 
        {'Authorization': acctoken}},config)
    .then((res) => res.data)
    .catch((err) => err.status);

    dispatch ({
        type: "OBJECT_ADD",
        payload: resdata['data']
    }); 
};

export const updatePostobj = (obj) => async dispatch => {
    let boardId = obj['id'];
    let resdata = '';
    
    let updateAPI = `${process.env.REACT_APP_BACKEND}` + `/api/boards/${boardId}`;
    let acctoken = localStorage.getItem('accessToken');
    const config = {"Content-Type" : 'application/json'};
    
    if(boardId !== undefined)
    {
        await wait(500);
        await axios.get(updateAPI, {headers : 
            {'Authorization': acctoken}},config)
        .then((res) => {
            resdata = res.data});
        dispatch ({
            type: "OBJECT_UPDATE",
            payload: resdata
        })
    }    
}

export const resetPostobj = () => ({
    type: "OBJECT_RESET",
})

