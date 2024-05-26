import React, { useState } from 'react'
import api from '../../../utils/api';

const MakePost = ({setPostList,setIsMakePostOpenModal}) => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [boardType, setBoardType] = useState('notice');
  const [errorMessage, setErrorMessage] = useState(null);
  const onClickMakeCompleteBtn = async() => {
    try {
      if(!title){
        throw new Error("제목을 입력하세요.")
      }
      if(!content){
        throw new Error("내용을 입력하세요.")
      } 
      const response = await api.post('/posting',{title,content,boardType})
      
      if(response.status === 200){
        setPostList(response.data);
        setIsMakePostOpenModal(false);
      }
    } catch (error) {
      setErrorMessage(error.message)
    }
  }

  const onClickCancelBtn = () => {
    setIsMakePostOpenModal(false);
  }
  return (
    <div className='modal'>
      <div className='modalOverlay'></div>
      
      <div className='makePostModal'>
        <textarea value={title} onChange={(e)=>{setTitle(e.target.value)}} className="titleInput"  placeholder='글제목'/>
        <textarea value={content} onChange={(e)=>{setContent(e.target.value)}}className='contentInput'  placeholder='내용' />
        <div className='categoryArea'>
          <select value={boardType} onChange={(e)=>{setBoardType(e.target.value)}}>
            <option value="notice">공지사항</option>
            <option value="free">자유게시판</option>
          </select>
        </div>

        <div className="errorMessageWrap">{errorMessage}</div>
        <div>
          <button
           onClick={onClickMakeCompleteBtn}
           className='completeBtn'>완료</button>
          <button 
            onClick={onClickCancelBtn}
            className='cancelBtn'>취소</button>
        </div>
      </div>
    </div>
  )
}

export default MakePost