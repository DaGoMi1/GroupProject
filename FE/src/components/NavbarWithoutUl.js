import React from 'react'
import { Link } from 'react-router-dom';


const NavbarWithoutUl = () => {
  return (
    <>
    <nav>
      <Link to='/mainPage'>
        <img className='kmou_logo' src='https://www.kmou.ac.kr/images/web/kmou/common/logo3.png' alt="kmou_logo"/>
      </Link>
      
      <button className='hamburger'><i class="fa-solid fa-bars"></i></button>
    </nav>
  </>
  )
}

export default NavbarWithoutUl