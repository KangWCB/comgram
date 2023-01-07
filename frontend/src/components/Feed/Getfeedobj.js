import axios from "axios";
import {useState, React, useEffect} from 'react';
const Getfeedobj = () => {
    const acctoken = localStorage.getItem('accessToken');
    const feedobj = {

    }

    axios.get('/api/boards/list', {headers : 
        {'Authorization': acctoken}})
    .then((res) => {
        console.log(res.data);
    })
    .catch((err) => {
        console.log(err);
    });


};

export default Getfeedobj;