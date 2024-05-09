import React, { useState } from 'react'
const AddModal = ({startDay,endDay,title,color,
  errorMessage,inputChange,onclickCompleteBtn,onClickCancelBtn}) => {

  return (
    <div className='modal'>
      <div className='modalOverlay'></div>
      <div className='addScheduleModal'>
        <h1>새로운 일정 추가</h1>
        <div className='modal_input_area'>
          <h3>시작날짜</h3>
          <input type="text" value={startDay} onChange={(e)=>{inputChange('startDay', e.target.value)}} placeholder='OOOO-OO-OO' />
          <h3>종료날짜</h3>
          <input type="text" value={endDay} onChange={(e)=>{inputChange('endDay', e.target.value)}} placeholder='OOOO-OO-OO' />
          <h3>제목</h3>
          <input type="text" value={title} onChange={(e)=>{inputChange('title', e.target.value)}}  placeholder='제목을 입력하세요' />
          <h3>배경색</h3>
          <input type="color" id='selectColor' value={color} onChange={(e)=>{inputChange('color', e.target.value)}}/>
        </div>

        <div className="errorMessageWrap">
            <h5>{errorMessage}</h5>
          </div>
        <button 
          onClick={onclickCompleteBtn}
          className='completeBtn'>완료</button> 
        <button 
          onClick={onClickCancelBtn}
          className='cancelBtn'>취소</button> 
      </div>
    </div>
  )
}

export default AddModal