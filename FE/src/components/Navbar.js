import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import Sideabar from './Sideabar'

const Navbar = () => {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  return (
    <>
      <nav>
        <Link to='/mainPage'>
          <img className='kmou_logo' src='https://www.kmou.ac.kr/images/web/kmou/common/logo3.png' alt="kmou_logo"/>
        </Link>
        <ul className='nav_menu'>
          <li>
            <button>공지사항</button>
          </li>
          <li>
            <button>게시판</button>
          </li>
          <li>
            <button>학과일정</button>
          </li>
          <li>
            <button>회의</button>
          </li>
          <li>
            <button>링크</button>
          </li>
        </ul>
        <button onClick = {()=>{setSidebarOpen(!sidebarOpen)}} className='hamburger'><i class="fa-solid fa-bars"></i></button>
      </nav>
      
      {sidebarOpen 
        ? <Sideabar sidebarOpen = {sidebarOpen} setSidebarOpen = {setSidebarOpen}/> 
        : null
          }
    </>
  )
}

export default Navbar