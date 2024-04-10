import React from 'react'
import api from '../../utils/api';

import { format ,startOfMonth, endOfMonth, startOfWeek, endOfWeek, addDays, getDay } from 'date-fns'
const RenderDate = ({ current }) => {
  const currentMonth = format(current,'M');
  const monthStart = startOfMonth(current);
  const monthEnd = endOfMonth(current);
  const weekStart = startOfWeek(monthStart);
  const weekEnd = endOfWeek(monthEnd);

  const dateList = [];
  let date = weekStart;
  let formattedDate = '';

  const onClickDate = (key) => {
    fetchDate(key);
  }

  const fetchDate = async (key) => {
    const response = await api.get('/main/test');
    console.log(response);
  }

  while(date <= weekEnd){
    const weekList = [];
    for(let i=0; i<7; i++){
      formattedDate = format(date,'d');
      const key = format(date,'yyyy-MM-dd');
      const classNames = 
        getDay(date) === 0 
        ? format(date,'M') === currentMonth
          ? 'date sun'
          : 'otherMonthDate sun'
        : getDay(date) === 6
          ? format(date,'M') === currentMonth
          ? 'date sat'
          : 'otherMonthDate sat'
        : format(date, 'M') === currentMonth
          ? 'date'
          : 'otherMonthDate'
      weekList.push(
        <td onClick = {()=>{onClickDate(key)}} className = {classNames} key = {key}>
            {formattedDate}
        </td>); 
      date = addDays(date,1);
    }
    dateList.push(weekList);
    console.log(dateList);  


  }
  return (
    <>
      {dateList.map((weekList,index)=>{
        return <tr>{weekList}</tr>
      })}
    </>
  )
}

export default RenderDate