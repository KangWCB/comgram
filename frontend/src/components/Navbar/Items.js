import {
    FaTh,
    FaUserAlt,
    FaThList
  } from "react-icons/fa";
  // https://react-icons.github.io/react-icons
  // 여기서 아이콘 검색
import { BsPencilSquare } from "react-icons/bs"
import { GrLogin, GrLogout} from "react-icons/gr"

export const Items = [
    {
        title: 'Home',
        url: '/',
        className: 'nav-links',
        icon: <FaTh/>
    },
    {
        title: 'Write',
        url: '/write',
        className: 'nav-links',
        icon: <BsPencilSquare/>
    },
    {
        title: 'Information',
        url: '/info',
        className: 'nav-links',
        icon: <FaUserAlt/>
    },
    {
        title: 'Login',
        url: '/login',
        className: 'nav-links',
        icon: <GrLogin/>
    },
]