import React from 'react'

const RenderDays = ({daysList}) => {
  const days = [];

  daysList.map((day)=>{
    days.push(<td className='days' key={day}>{day}</td>)
  })

  return (
    <>
      <tr>
        {days}
      </tr>
    </>
  )
}

export default RenderDays