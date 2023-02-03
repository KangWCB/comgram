import {useState, React, useEffect} from 'react';
import {Link, useNavigate, useLocation, useParams} from 'react-router-dom'
import axios from 'axios';

const OAuthLogin = () => {
    const loc = useLocation();
    const navigate = useNavigate();
    let acctoken = '';
    useEffect(() => {
        console.log(loc);
        const searchParams = new URLSearchParams(loc.search);
        acctoken = searchParams.get('token');
        console.log(searchParams)
        console.log(acctoken);
        localStorage.setItem("accessToken",acctoken);
    },[]);

    useEffect(() => {
        if(acctoken)
        {
            window.location.href = "/";
        }
    },[acctoken]);
    return (
        <div>
            Loading...
        </div>
    );

};
export default OAuthLogin;