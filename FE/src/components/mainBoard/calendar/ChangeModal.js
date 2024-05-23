import React, {useState} from 'react'
import api from '../../../utils/api';
const ChangeModal = ({schoolSchedule,setSchoolSchedule,setIsOpenChangeModal}) => {

  const [title, setTitle] = useState('');
  const [changeTitle, setChangeTitle] = useState('');
  const [startDay, setStartDay] = useState('');
  const [endDay, setEndDay] = useState('');
  const [color, setColor] = useState('#000000');
  const [errorMessage, setErrorMessage] = useState('');

  const handleCompleteBtn = async() => {
    try {
      const schedule = schoolSchedule.find(schedule => schedule.title === title);
      const { id } = schedule;
      const response = await api.patch(`/admin/schedule/update`,{
          id, startDay, endDay, color, changeTitle
      })
      if(response.status === 200) {
        const updateSchedule = await api.get('schedule/list/data');
        setSchoolSchedule(updateSchedule.data);
        setIsOpenChangeModal(false);
      } else {
        throw new Error("업데이트 실패");
      }
    } catch (error) {
      setErrorMessage(error.message);
    } 
  }

  const onClickCancelBtn = () =>{
    setIsOpenChangeModal(false);
  }
  return (
    <div className='modal'>
      <div className='modalOverlay'></div>
      <div className='scheduleModal'>
        <h1>일정 변경하기</h1>
        <div className='modal_input_area'>
          <h3>변경하려는 일정의 제목</h3>
          <input type="text" value={title} onChange={(e)=>{setTitle(e.target.value)}}  placeholder='제목을 입력하세요' />
          <h3>(변경)시작날짜</h3>
          <input type="text" value={startDay} onChange={(e)=>{setStartDay(e.target.value)}} placeholder='OOOO-OO-OO' />
          <h3>(변경)종료날짜</h3>
          <input type="text" value={endDay} onChange={(e)=>{setEndDay(e.target.value)}} placeholder='OOOO-OO-OO' />
          <h3>(변경)제목</h3>
          <input type="text" value={changeTitle} onChange={(e)=>{setChangeTitle(e.target.value)}} placeholder='제목을 입력하세요' />
          <h3>(변경)배경색</h3>
          <input type="color" id='selectColor' value={color} onChange={(e)=>{setColor(e.target.value)}}/>
        </div>

        <div className="errorMessageWrap">
            <h5>{errorMessage}</h5>
          </div>
        <button 
          onClick={handleCompleteBtn}
          className='completeBtn'>완료</button> 
        <button 
          onClick={onClickCancelBtn}
          className='cancelBtn'>취소</button> 
      </div>
    </div>
  )
}

export default ChangeModal