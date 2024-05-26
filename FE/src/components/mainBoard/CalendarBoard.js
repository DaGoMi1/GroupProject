import React, { useEffect, useState } from 'react'

import AddModal from './calendar/AddModal';
import DeleteModal from './calendar/DeleteModal';
import ChangeModal from './calendar/ChangeModal';
import api from '../../utils/api';
import Calendar from './calendar/Calendar';
import { format, isWithinInterval, parseISO, addDays, startOfWeek, endOfWeek } from 'date-fns';

const CalendarBoard = React.forwardRef((props,ref) => {
  const [isOpenAddModal, setIsOpenAddModal] = useState(false);
  const [isOpenChangeModal, setIsOpenChangeModal] = useState(false);
  const [isOpenDeleteModal, setIsOpenDeleteModal] = useState(false);
  const [schoolSchedule, setSchoolSchedule] = useState([]);
  
  const fetchSchoolSchedule = async () => {
    try {
      const response = await api.get('/schedule/list/data');
      setSchoolSchedule(response.data);
    } catch (error) {
      console.log(error.message);      
    }
  }

  useEffect(()=>{
    fetchSchoolSchedule();
  },[])
  
  const onClickCompleteBtn = async (newSchedule) => {
    try {
      const response = await api.post('/admin/schedule', newSchedule);
      if(response.status === 200){
        const upadateSchedule = await api.get('/schedule/list/data');
        setSchoolSchedule(upadateSchedule.data);
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
            schoolSchedule={schoolSchedule}
            setSchoolSchedule={setSchoolSchedule}
            setIsOpenChangeModal={setIsOpenChangeModal}
            />
        : null}
      {isOpenDeleteModal 
        ? <DeleteModal 
            schoolSchedule = {schoolSchedule}
            setSchoolSchedule = {setSchoolSchedule}
            setIsOpenDeleteModal={setIsOpenDeleteModal}/>        
        : null}

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