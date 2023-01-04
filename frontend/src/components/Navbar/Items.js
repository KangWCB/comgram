import {
    FaTh,
    FaBars,
    FaUserAlt,
    FaRegChartBar,
    FaCommentAlt,
    FaShoppingBag,
    FaThList
  } from "react-icons/fa";
  // https://react-icons.github.io/react-icons
  // 여기서 아이콘 검색


export const Items = [
    {
        title: 'Home',
        url: '/',
        className: 'nav-links',
        icon: <FaTh/>
    },
    {
        title: 'Login',
        url: '/login',
        className: 'nav-links',
        icon: <FaUserAlt/>
    },
    {
        title: 'Logout',
        url: '#',
        className: 'nav-links',
        icon: <FaThList/>
    }
]