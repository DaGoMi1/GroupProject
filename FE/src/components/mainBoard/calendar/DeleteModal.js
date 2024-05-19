import React, { useState } from 'react'
import api from '../../../utils/api';
const DeleteModal = ({schoolSchedule,setIsOpenDeleteModal, setSchoolSchedule}) => {
  const [title, setTitle] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const onClickCompleteBtn = async () => {
    try {
      const schedule = schoolSchedule.find( schedule => schedule.title === title);
      if(!schedule){
        throw new Error("해당 일정을 찾을 수 없습니다.")
      }
      const { _id } = schedule;
      const response = await api.delete('/admin/schedule', {
        data: {_id}
      });

      if(response.status === 200){
        const newSchedule = await api.get('/schedule/list/data');
        setSchoolSchedule(newSchedule.data.data);
        setIsOpenDeleteModal(false);
      } else {
        throw new Error("삭제 실패");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  }

  const onClickCancelBtn = () => {
    setIsOpenDeleteModal(false);
  }

  return (
    <div className='modal'>
      <div className='modalOverlay'></div>
      <div className='scheduleModal deleteModal'>
        <h1>일정 삭제하기</h1>
        <div className='modal_input_area'>
          <h3>제목</h3>
          <input type="text" value={title} onChange={(e)=>{setTitle(e.target.value)}}  placeholder='삭제할 일정 제목' />
        </div>

        <div className="errorMessageWrap">
            <h5>{errorMessage}</h5>
          </div>
        <button 
          onClick={onClickCompleteBtn}
          className='completeBtn'>완료</button> 
        <button 
          onClick={onClickCancelBtn}
          className='cancelBtn'>취소</button> 
      </div>
    </div>
  )
}

export default DeleteModal