import React, { useEffect, useState } from 'react'

import AddModal from './calendar/AddModal';
import DeleteModal from './calendar/DeleteModal';
import ChangeModal from './calendar/ChangeModal';
import api from '../../utils/api';
import Calendar from './calendar/Calendar';
const CalendarBoard = React.forwardRef((props,ref) => {
  const [isOpenAddModal, setIsOpenAddModal] = useState(false);
  const [isOpenChangeModal, setIsOpenChangeModal] = useState(false);
  const [isOpenDeleteModal, setIsOpenDeleteModal] = useState(false);

  const [schoolSchedule, setSchoolSchedule] = useState([]);
  const [modalData, setModalData] = useState({
    startDay: '',
    endDay: '',
    title: '',
    color: '#000000',
    errorMessage: '',
  })
  
  const { startDay, endDay, title, color, errorMessage } = modalData;

  // modalData를 한번에 관리할 때 set을 개개인으로 못쓰므로 change함수 만들기
  const inputChange = (key, value) => {
    setModalData({...modalData, [key]: value});
  }

  const onclickCompleteBtn = async () => {
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
      if(!title){
        throw new Error("제목을 입력하세요");
      }

      const response = await api.post('/admin/schedule/add', {startDay,endDay,title,color});

      const newSchedule = {
        startDay: response.data.data.startDay,
        endDay: response.data.data.endDay,
        title: response.data.data.title,
        color: response.data.data.color,
      }
      setSchoolSchedule([...schoolSchedule, newSchedule]);
      setIsOpenAddModal(false);
      
      setModalData({
        startDay: '',
        endDay: '',
        title: '',
        color: '#000000',
        errorMessage: '',
      })
    } catch (error) {
      setModalData({...modalData, errorMessage : error.message});
    }
  }

  const onClickCancelBtn = () => {
    setModalData({
      startDay: '',
      endDay: '',
      title: '',
      color: '#000000',
      errorMessage: '',
    })
    setIsOpenAddModal(false);
  }

  return (
    <>
      {isOpenAddModal 
        ? <AddModal 
            {...modalData}
            inputChange = {inputChange}
            onclickCompleteBtn={onclickCompleteBtn}
            onClickCancelBtn={onClickCancelBtn}
            /> 
        : null}
      {isOpenChangeModal ? <ChangeModal setIsOpenChangeModal={setIsOpenChangeModal}/> : null}
      {isOpenDeleteModal ? <DeleteModal setIsOpenDeleteModal={setIsOpenDeleteModal}/> : null}
      <div className="contents">
        <div className="box category" ref={ref}>학과일정</div>
        <div className='calendarBoard'>
          <div className='box calendar_area'>
            <Calendar schoolSchedule={schoolSchedule}/>
          </div>
          <div className='calendar_right_area'>
            <div>
              <div className="today">
                <div className="box category">오늘</div>
                <div className="box">일정 없음</div>
              </div>
              <div className="tomorrow">
                <div className="box category">내일</div>
                <div className="box">일정 없음</div>
              </div>
              <div className="thisWeek">
                <div className="box category">이번 주</div>
                <div className="box">일정 없음</div>
              </div>
            </div>

            <div className='calendar_btn_area box'>
              <button onClick={()=>{setIsOpenAddModal(true)}}>일정 추가하기</button>
              <button onClick={()=>{setIsOpenChangeModal(true)}}>일정 변경하기</button>
              <button onClick={()=>{setIsOpenDeleteModal(true)}}>일정 삭제하기</button>
            </div>
          </div>
        </div>
      </div>
    </>
  )
})

export default CalendarBoard