import React, { useRef, useState } from 'react'
import { Link } from 'react-router-dom'
import Sideabar from './Sideabar'

const Navbar = ({user, sectionRef, setChangeComponent}) => {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const moveToScroll = (ref) => {
    ref.current.scrollIntoView({ behavior : 'smooth'});
  }

  const navButtonList = ['공지사항','게시판','학과일정','회의','링크'];
  const refList = Object.entries(sectionRef)
  return (
    <>
      <nav>
        <Link to='/'>
          <img className='kmou_logo' src='https://www.kmou.ac.kr/images/web/kmou/common/logo3.png' alt="kmou_logo"/>
        </Link>
        <ul className='nav_menu'>
          {
            navButtonList.map((v,i)=>{
              return <li><button onClick={()=>{moveToScroll(refList[i][1])}}>{v}</button></li>
            })
          }
        </ul>
        <button onClick = {()=>{setSidebarOpen(!sidebarOpen)}} className='hamburger'><i class="fa-solid fa-bars"></i></button>
      </nav>
      
      {sidebarOpen 
        ? <Sideabar 
            sidebarOpen = {sidebarOpen} 
            setSidebarOpen = {setSidebarOpen} 
            user={user}
            setChangeComponent={setChangeComponent}/> 
        : null
          }
    </>
  )
}

export default Navbar