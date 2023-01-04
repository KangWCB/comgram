import React ,{ useState }from "react";
import axios from "axios";

const Profile = (inherit_token) => {
    const acctoken = inherit_token;
    const [profileImg, setProfileImg] = useState("");
    

    const imgHandler = (e) => {
        setProfileImg(URL.createObjectURL(e.target.files[0]));
    };


    const modifyProfile = (token, img) => {
        const formData = new FormData();
        formData.append('image', img);
        axios({
            url: '/api/members/profile',
            method: 'POST',
            data: formData,
            Headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization': token,
            }
        })
        .then(res => {
            console.log(res.data);
        })
        .catch(err => {
            console.log(err);
        })
    };

    return (
        <div>
            <input onChange={imgHandler}type="file" accept="image/*" ></input>
            <button onClick={modifyProfile(acctoken, profileImg)}>업로드</button>
        </div>
    )

};

export default Profile;