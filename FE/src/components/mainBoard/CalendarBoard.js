import React, { useEffect, useState } from 'react'

import AddModal from './calendar/AddModal';
import DeleteModal from './calendar/DeleteModal';
import ChangeModal from './calendar/ChangeModal';
import api from '../../utils/api';
import Calendar from './calendar/Calendar';
import { format, isWithinInterval, parseISO, addDays, startOfWeek, endOfWeek } from 'date-fns';
// 1. 서버에서 schoolSchedule 이 있는지 먼저 받아온다. -> RenderDate로 넘겨줘서 그린다.
// 2. 일정 추가 시 서버에 일정을 등록한다.
// 3. 일정을 등록하면 다시 받아와 SchoolSchedule에 저장한다. -> RenderDate로 넘겨줘서 그린다.
// 그럼 RenderDate는 schoolSchedule이 변경될 때마다 그리는 것이다.
// 처음에 서버에서 그리고 변경될때마다 그리는거만 해결하면됨
const CalendarBoard = React.forwardRef((props,ref) => {
  const [isOpenAddModal, setIsOpenAddModal] = useState(false);
  const [isOpenChangeModal, setIsOpenChangeModal] = useState(false);
  const [isOpenDeleteModal, setIsOpenDeleteModal] = useState(false);
  const [schoolSchedule, setSchoolSchedule] = useState([]);

  const fetchSchoolSchedule = async () => {
    try {
      const response = await api.get('/schedule/list/data');
      setSchoolSchedule(response.data.data);
    } catch (error) {
      console.log(error.message);      
    }
  }

  useEffect(()=>{
    fetchSchoolSchedule();
  },[])
  
  const onClickCompleteBtn = async (newSchedule) => {
    try {
      const response = await api.post('/admin/schedule/add', newSchedule);
      if(response.status === 200){
        const upadateSchedule = await api.get('/schedule/list/data');
        setSchoolSchedule(upadateSchedule.data.data);
        setIsOpenAddModal(false);
      } else {
        throw new Error("스케줄 추가 실패");
      }
    } catch (error) {
      console.log("데이터 불러오기 실패", error.message);
    }
  }

  const onClickCancelBtn = () => {
    setIsOpenAddModal(false);
    setIsOpenChangeModal(false);
  }

  const today = format(new Date(), 'yyyy-MM-dd');
  const todaySchedules = schoolSchedule.filter(schedule =>
      isWithinInterval(parseISO(today), {
      start: parseISO(schedule.startDay),
      end: parseISO(schedule.endDay)
    })
  );

  const tomorrow = format(addDays(today,1), 'yyyy-MM-dd');
  
  const tomorrowSchedules = schoolSchedule.filter(schedule=>
    isWithinInterval(parseISO(tomorrow),{
      start: parseISO(schedule.startDay),
      end: parseISO(schedule.endDay)
    })
  )


  const startOfThisWeek = format(startOfWeek(today), 'yyyy-MM-dd');
  const endOfThisWeek = format(endOfWeek(today), 'yyyy-MM-dd');
  
  const thisweekSchedules = schoolSchedule.filter(schedule=>
    isWithinInterval(parseISO(schedule.startDay),{
      start: parseISO(startOfThisWeek),
      end: parseISO(endOfThisWeek)
    })||
    isWithinInterval(parseISO(schedule.endDay),{
      start: parseISO(startOfThisWeek),
      end: parseISO(endOfThisWeek)
    })
  )

  return (
    <>
      {isOpenAddModal 
        ? <AddModal 
            onClickCompleteBtn={onClickCompleteBtn}
            onClickCancelBtn={onClickCancelBtn}
            /> 
        : null}
      {isOpenChangeModal 
        ? <ChangeModal 
            setIsOpenChangeModal={setIsOpenChangeModal}
            onClickCancelBtn={onClickCancelBtn} 
            />
        : null}
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
                <div className="box scheduleStyle">
                  {todaySchedules.map(schedule=>{
                    return <span style={{backgroundColor: schedule.color, color:"white", padding: '0.3rem'}}>-{schedule.title}<br></br></span>
                  })}
                </div>
              </div>
              <div className="tomorrow">
                <div className="box category">내일</div>
                <div className="box scheduleStyle">
                  {tomorrowSchedules.map(schedule=>{
                    return <span style={{backgroundColor: schedule.color, color:"white", padding: '0.3rem'}}>-{schedule.title}<br></br></span>
                  })}
                </div>
              </div>
              <div className="thisWeek">
                <div className="box category">이번 주</div>
                <div className="box scheduleStyle">
                  {thisweekSchedules.map(schedule=>{
                    return <span style={{backgroundColor: schedule.color, color:"white", padding: '0.3rem'}}>-{schedule.title}<br></br></span>
                  })}
                </div>
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