import React, {useState} from "react";
import { FaBars } from "react-icons/fa";

import { NavLink } from 'react-router-dom';

import styles from './Navbar.module.css';
import {Items} from './Items';

const Navbar = () => {

  const[isOpen ,setIsOpen] = useState(false);
  const toggle = () => setIsOpen (!isOpen);
  
  return (
    <div className={styles.container}>
      <div style={{width: isOpen ? "200px" : "50px"}} className={styles.Navbar}>
        <div className={styles.top_section}>
          <h1 style={{display: isOpen ? "block" : "none"}} className={styles.logo}>Logo</h1>
          <div style={{marginLeft: isOpen ? "50px" : "0px"}} className={styles.bars}>
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
};

export default Navbar;