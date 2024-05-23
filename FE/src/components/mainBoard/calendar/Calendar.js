import React from 'react'
import RenderHeader from './RenderHeader'
import RenderDays from './RenderDays'
import RenderDate from './RenderDate'
import { useState } from 'react'
import { addMonths, subMonths } from 'date-fns'


const Calendar = ({schoolSchedule}) => {
  const [current, setCurrent] = useState(new Date());
  const daysList = ['일', '월', '화', '수', '목', '금', '토'];

  const goPrevMonth = () => {
    setCurrent(subMonths(current,1))
  }

  const goNextMonth = () => {
    setCurrent(addMonths(current,1));
  }
  return (
    <>
      <div className='calendar'>
          <RenderHeader current={current} goNextMonth={goNextMonth} goPrevMonth={goPrevMonth}/>
        <table>
          <RenderDays daysList={daysList} />
          <RenderDate current={current} schoolSchedule={schoolSchedule}/>
        </table>
      </div>
    </>

  )
}

export default Calendar