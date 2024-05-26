import React, { useEffect,useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../../utils/api'
const NoticeBoard = React.forwardRef(({setChangeComponent},ref) => {
  const [noticeList, setNoticeList] = useState([]);

  const getPostList = async () => {
    try {
      const response = await api.post('/posting/list',{boardType: 'notice'});
      if(response.status === 200){
        const sliceArr = response.data.slice(0,3);
        setNoticeList(sliceArr);
      } else {
        throw new Error(response.data);
      }
    } catch (error) {
      console.log(error.message);
    }
  }

  useEffect(()=>{getPostList()},[])
  return (
    <div className='contents'>
      <div className="box category" ref={ref}>공지사항</div>
      <div className="box">
        <ul>
          {noticeList.length > 0 ? (
            <ul>
              {noticeList.map((notice, index) => {
                return <li key={index}>{notice.title}</li>
              })}
            </ul>
          ) : (
            <p>공지사항이 없습니다.</p>
          )}
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