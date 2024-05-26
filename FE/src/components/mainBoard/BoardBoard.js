import React, { useState , useEffect} from 'react'
import { Link } from 'react-router-dom'
import api from '../../utils/api';
const BoardBoard = React.forwardRef(({setChangeComponent}, ref) => {
  const [freeList, setFreeList] = useState([]);

  const getPostList = async () => {
    try {
      const response = await api.post('/posting/list',{boardType: 'free'});
      if(response.status === 200){
        const sliceArr = response.data.slice(0,3);
        setFreeList(sliceArr);
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
      <div className="box category" ref={ref}>게시판</div>
      <div className="box">
        <ul>
          {freeList.length > 0 ? (
              <ul>
                {freeList.map((free, index) => {
                  return <li key={index}>{free.title}</li>
                })}
              </ul>
            ) : (
              <p>글이 없습니다.</p>
            )}
        </ul>

        <div className='go_allPage_area'>
          <Link className='go_allPage' to='/myPage'>
            <button onClick={()=>{setChangeComponent('board')}}>전체 글 보러가기</button>
          </Link>
        </div>
      </div>
    </div>
  )
})

export default BoardBoard