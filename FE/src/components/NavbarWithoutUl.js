import React, {useState} from 'react'
import { Link } from 'react-router-dom';
import Sideabar from './Sideabar';

const NavbarWithoutUl = ({user, setChangeComponent}) => {
  const [sidebarOpen, setSidebarOpen] = useState(null);
  return (
    <>
    <nav>
      <Link to='/'>
        <img className='kmou_logo' src='https://www.kmou.ac.kr/images/web/kmou/common/logo3.png' alt="kmou_logo"/>
      </Link>
      
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

export default NavbarWithoutUl