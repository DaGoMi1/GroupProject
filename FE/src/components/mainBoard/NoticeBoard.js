import React from 'react'
import { Link } from 'react-router-dom'
const NoticeBoard = React.forwardRef(({setChangeComponent},ref) => {
  
  return (
    <div className='contents'>
      <div className="box category" ref={ref}>공지사항</div>
      <div className="box">
        <ul>
          <li>공지 1</li>
          <li>공지 2</li>
          <li>공지 3</li>
        </ul>

        <div className='go_allPage_area'>
          <Link to='/myPage' className='go_allPage'>
            <button onClick={()=>{setChangeComponent('notice')}}>전체 글 보러가기</button>
          </Link>
        </div>
      </div>
    </div>
  )
})

export default NoticeBoard