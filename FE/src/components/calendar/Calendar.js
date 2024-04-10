import React from 'react'
import RenderHeader from './RenderHeader'
import RenderDays from './RenderDays'
import RenderDate from './RenderDate'
import { useState } from 'react'
import { addMonths, subMonths } from 'date-fns'

const Calendar = () => {
  const [current, setCurrent] = useState(new Date());

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
          <RenderDays />
          <RenderDate current={current}/>
        </table>
      </div>
    </>

  )
}

export default Calendar