import React from 'react';
import { format } from 'date-fns';

const RenderUserHeader = ({ current, goNextMonth, goPrevMonth }) => {
  return (
    <div className='calendar_header user_header'>
      <div className='previous' onClick={goPrevMonth}>
        <span> &lt; </span>
      </div>
      <div>
        <span style={{padding:'20px', cursor:'pointer'}}>{format(current, 'yyyy년 M월')}</span>
      </div>
      <div className='next' onClick={goNextMonth}>
        <span style={{cursor:'pointer'}}> &gt; </span>
      </div>
    </div>
  )
}

export default RenderUserHeader;
