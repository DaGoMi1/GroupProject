import React from 'react'
import { Link } from 'react-router-dom'
const BoardBoard = () => {
  return (
    <div className='contents'>
      <div className="box category" id='boardboard'>게시판</div>
      <div className="box">
        <ul>
          <li>글 1</li>
          <li>글 2</li>
          <li>글 3</li>
        </ul>

        <div className='go_allPage_area'>
          <Link className='go_allPage'>전체 글 보러가기</Link>
        </div>
      </div>
    </div>
  )
}

export default BoardBoard