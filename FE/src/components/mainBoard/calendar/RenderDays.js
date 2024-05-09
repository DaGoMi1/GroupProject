import React from 'react'

const RenderDays = () => {
  const daysList = ['일', '월', '화', '수', '목', '금', '토'];
  const days = [];

  daysList.map((day)=>{
    days.push(<td className='days' key={day}>{day}</td>)
  })
  console.log(days);
  return (
    <>
      <tr>
        {days}
      </tr>
    </>
  )
}

export default RenderDays