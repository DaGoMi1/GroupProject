import React from 'react'
import {format} from 'date-fns'

const RenderHeader = ({current, goNextMonth, goPrevMonth}) => {
  
  return (
    <>
      <div className='calendar_header'>
        <button className='calendar_btns' onClick={goPrevMonth}>&#60;</button>
        <div className='yearAndMonth'>
          <h2 className='currentYearArea'>{format(current,'yyyy')}년</h2>
          <h2>{format(current,'M')}월</h2>
        </div>
        <button className='calendar_btns' onClick={goNextMonth}>&#62;</button>
      </div>
    </>
  )
}

export default RenderHeader