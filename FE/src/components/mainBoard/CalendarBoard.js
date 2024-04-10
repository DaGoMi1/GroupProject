import React from 'react'
import Calendar from '../calendar/Calendar'

const CalendarBoard = () => {
  return (
    <div className="contents">
      <div className="box category" id='calendarBoard'>학과일정</div>
      <div className='calendarBoard'>
        <div className='box calendar_area'>
          <Calendar/>
        </div>
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
      </div>
    </div>
  )
}

export default CalendarBoard