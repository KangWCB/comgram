import React ,{ useState }from "react";
import axios from "axios";

const Profile = (inherit_token) => {
    const acctoken = inherit_token;
    const [profileImg, setProfileImg] = useState("");
    

    const imgHandler = (e) => {
        setProfileImg(URL.createObjectURL(e.target.files[0]));
        console.log("사진세팅완");
    };


    const modifyProfile = (token, img) => {
        console.log("호출");
        const formData = new FormData();
        formData.append('image', img);
        let tmp = localStorage.getItem('userId');
        let tmp2 = `api/members/${tmp}/update`
        console.log(tmp2);
        axios({
            url: tmp2,
            method: 'POST',
            data: formData,
            Headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization': token,
            }
        })
        .then(res => {
            console.log(`${res.data}`);
        })
        .catch(err => {
            console.log(err);
        })
    };

    return (
        <div>
            <input onChange={(e) => imgHandler(e)}type="file" accept="image/*" ></input>
            <button onClick={() => modifyProfile(acctoken, profileImg)}>업로드</button>
        </div>
    )

};

export default Profile;