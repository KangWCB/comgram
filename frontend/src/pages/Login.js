import {useState, React} from 'react';
import ReactDOM from 'react-dom/client';
import axios from 'axios';

const LoginPage = () => {
    console.log("hi1");
    const [email, setEmail] = useState(""); 
    const [password, setPassword] = useState(""); 
    const [name, setName] = useState(""); 
    const [nickname, setNickname] = useState(""); 
    const userObject = {
        'email' : email,
        'password' : password,
        'name' : name,
        'nickname' : nickname,
    }

    const registerHandler = () => {
        console.log(userObject);
        const config = {"Content-Type" : 'application/json'};
        axios.post('/api/members/register', userObject, config)
        .then((res) => {
            console.log(res.data);
        })
        .catch((err) => {
            console.log(err);
        });
    };
    return (
        <div className="App">
            <div>
                <label>Email</label>
                <input onChange={(e) => setEmail(e.target.value)}/>
                <br/>
                <label>pw</label>
                <input onChange={(e) => setPassword(e.target.value)}/>
                <br/>
                <label>Name</label>
                <input onChange={(e) => setName(e.target.value)}/>
                <br/>
                <label>Nickname</label>
                <input onChange={(e) => setNickname(e.target.value)}/>
                <br/>
                <button onClick={() => registerHandler()}>가입</button>
            </div>
        </div>
    )
};
export default LoginPage;