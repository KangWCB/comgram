import { useState, React} from 'react';
import ReactDOM from 'react-dom/client';
import axios from 'axios';
const LoginPage = () => {
    console.log("hi1");
    const [email, setEmail] = useState(""); 
    const [pw, setPw] = useState(""); 
    const [name, setName] = useState(""); 
    const [nickname, setNickname] = useState(""); 
    const userObject = {
        email,
        pw,
        name,
        nickname,
    }
    const registerHandler = () => {
        console.log(userObject);
        console.log("hi");
        axios.post('/api/members/register', userObject)
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
                <input onChange={(e) => setPw(e.target.value)}/>
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