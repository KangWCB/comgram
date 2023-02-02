import {
    FaTh,
    FaUserAlt,
    FaThList
  } from "react-icons/fa";
  // https://react-icons.github.io/react-icons
  // 여기서 아이콘 검색
import { BsPencilFill, BsSearch } from "react-icons/bs"
import { GrLogin, GrLogout } from "react-icons/gr"

export const Items = [
    {
        title: '홈',
        url: '/',
        className: 'nav-links',
        icon: <FaTh/>
    },
    {
        title: '글 쓰기',
        url: '/write',
        className: 'nav-links',
        icon: <BsPencilFill/>
    },
    {
        title: '내 정보',
        url: '/info',
        className: 'nav-links',
        icon: <FaUserAlt/>
    },
    {
        title: '로그인',
        url: '/login',
        className: 'nav-links',
        icon: <GrLogin/>
    },
    {
        title: '검색',
        url: '/search',
        className: 'nav-links',
        icon: <BsSearch/>
    },
]