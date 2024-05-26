import React from 'react';
import RenderUserHeader from './RenderUserHeader';
import RenderUserDays from './RenderUserDays';
import RenderUserDate from './RenderUserDate';
import { useState } from 'react';
import { addMonths, subMonths } from 'date-fns';

const UserCalendar = ({ schedule, setSelectedSchedule }) => {
  const [current, setCurrent] = useState(new Date());
  const daysList = ['일', '월', '화', '수', '목', '금', '토'];

  const goPrevMonth = () => {
    setCurrent(subMonths(current, 1));
  }

  const goNextMonth = () => {
    setCurrent(addMonths(current, 1));
  }

  return (
    <>
      <div className='calendar'>
        <RenderUserHeader current={current} goNextMonth={goNextMonth} goPrevMonth={goPrevMonth} />
        <table>
          <RenderUserDays daysList={daysList} />
          <RenderUserDate current={current} schedule={schedule} />
        </table>
      </div>
    </>
  )
}

export default UserCalendar;
