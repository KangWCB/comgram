import {useState, React, useEffect} from 'react';
import {Link, useNavigate, useLocation, useParams} from 'react-router-dom'


const OAuthLogin = () => {
    const loc = useLocation();
    let acctoken = '';
    let userid = '';
    useEffect(() => {
        const searchParams = new URLSearchParams(loc.search);
        acctoken = searchParams.get('token');
        userid = searchParams.get('id');
        localStorage.setItem("userId", userid);
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