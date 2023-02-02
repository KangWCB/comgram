import React, {useState} from "react";
import { FaBars } from "react-icons/fa";

import { useNavigate, NavLink, useLocation } from 'react-router-dom';
import { GrLogin, GrLogout} from "react-icons/gr"
import styles from './Navbar.module.css';
import {Items} from './Items';

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const toggle = () => setIsOpen(!isOpen);
  const navigate = useNavigate();
  let location = useLocation();
  
  if(location.pathname != "/login") {
    return (
      <div className={styles.container}>
        <div style={{width: isOpen ? "200px" : "50px"}} className={styles.Navbar}>
          <div className={styles.top_section}>
            <img onClick={() => navigate('/')}src='img/comgram-logo.png'style={{display: isOpen ? "block" : "none"}} className={styles.logo}/>
            <div style={{marginLeft: isOpen ? "30px" : "0px"}} className={styles.bars}>
              <FaBars onClick={toggle}/>
            </div>
          </div>
          {
            Items.map((item, index)=>(
              <NavLink to={item.url} key={index} className={styles.link}>
                <div className={styles.icon}>{item.icon}</div>
                <div style={{display: isOpen ? "block" : "none"}} className={styles.link_text}>{item.title}</div>
              </NavLink>
            ))
          }
          
        </div>
      </div>
    );
  }
  
};

export default Navbar;