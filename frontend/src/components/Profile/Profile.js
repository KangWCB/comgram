import React ,{ useState }from "react";
import axios from "axios";
import styles from './Profile.module.css';

const Profile = () => {
    const acctoken = localStorage.getItem('accessToken');
    const userid = localStorage.getItem('userId');
    const [profileImg, setProfileImg] = useState();
    

    const imgHandler = (e) => {
        setProfileImg(e.target.files[0]);
    };
    

    const modifyProfile = (token, img) => {
        console.log("호출");
        const formData = new FormData();
        formData.append('photo', img);
        let tmp2 = `api/members/${userid}/update`
        console.log(img);
        console.log(formData);
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
        <div className={styles.container}>
            <div className={styles.container_form}>
                <div className={styles.box}>
                </div>
                <input onChange={(e) => imgHandler(e)}type="file" accept="image/*" ></input>
                <button onClick={() => modifyProfile(acctoken, profileImg)}>업로드</button>
            </div>
        </div>
    )

};

export default Profile;