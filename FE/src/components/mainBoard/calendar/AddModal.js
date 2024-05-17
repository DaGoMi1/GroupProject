import React, { useState } from 'react'
const AddModal = ({onClickCompleteBtn,onClickCancelBtn}) => {
  const [startDay, setStartDay] = useState('');
  const [endDay, setEndDay] = useState('');
  const [title, setTitle] = useState('');
  const [color, setColor] = useState('#000000');
  const [errorMessage, setErrorMessage] = useState('');

  const handleCompleteBtn = () => {
    const regex = /^\d{4}-\d{2}-\d{2}$/;
    try {
      if(!startDay){
        throw new Error("시작날짜를 입력하세요");
      }
      if(!regex.test(startDay)){
        throw new Error("시작날짜를 형식에 맞게 입력하세요");
      }
      if(!regex.test(endDay)){
        throw new Error("종료날짜를 형식에 맞게 입력하세요");
      }
      if(!endDay){
        throw new Error("종료날짜를 입력하세요");
      }
      if(new Date(endDay) < new Date(startDay)){
        throw new Error("종료날짜가 시작날짜보다 앞섭니다.")
      }
      if(!title){
        throw new Error("제목을 입력하세요");
      }
      onClickCompleteBtn({ startDay, endDay, title, color});
      
    } catch (error) {
      setErrorMessage(error.message);
    }
  };
  return (
    <div className='modal'>
      <div className='modalOverlay'></div>
      <div className='scheduleModal'>
        <h1>새로운 일정 추가</h1>
        <div className='modal_input_area'>
          <h3>시작날짜</h3>
          <input type="text" value={startDay} onChange={(e)=>{setStartDay(e.target.value)}} placeholder='OOOO-OO-OO' />
          <h3>종료날짜</h3>
          <input type="text" value={endDay} onChange={(e)=>{setEndDay(e.target.value)}} placeholder='OOOO-OO-OO' />
          <h3>제목</h3>
          <input type="text" value={title} onChange={(e)=>{setTitle(e.target.value)}}  placeholder='제목을 입력하세요' />
          <h3>배경색</h3>
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

export default AddModal