import React, { useEffect, useState } from 'react'
import api from '../../../utils/api';
import { format ,startOfMonth, endOfMonth, startOfWeek, endOfWeek, addDays, getDay, isWithinInterval, isSameDay ,parseISO } from 'date-fns'

const RenderDate = ({ current, schoolSchedule }) => {
  const [dateList, setDateList] = useState([]);

  useEffect(() => {
    makeCalendarDate();
  }, [current, schoolSchedule]); // current와 schoolSchedule이 변경될 때마다 useEffect가 호출됨
  
  console.log(schoolSchedule);

  const currentMonth = format(current, 'M');
  const monthStart = startOfMonth(current);
  const monthEnd = endOfMonth(current);
  const weekStart = startOfWeek(monthStart);
  const weekEnd = endOfWeek(monthEnd);
  const today = format(new Date(), 'yyyy-MM-dd');

  const makeCalendarDate = () => {
    const tempDateList = [];
    let date = weekStart;

    while (date <= weekEnd) {
      const weekList = [];
      for (let i = 0; i < 7; i++) {
        const formattedDate = format(date, 'd');
        const key = format(date, 'yyyy-MM-dd');
        const classNames =
          getDay(date) === 0
            ? format(date, 'M') === currentMonth
              ? 'date sun'
              : 'otherMonthDate sun'
            : getDay(date) === 6
            ? format(date, 'M') === currentMonth
              ? 'date sat'
              : 'otherMonthDate sat'
            : format(date, 'M') === currentMonth
            ? 'date'
            : 'otherMonthDate';
        
        const schedules = schoolSchedule.filter(schedule =>
          isWithinInterval(parseISO(key), {
            start: parseISO(schedule.startDay),
            end: parseISO(schedule.endDay)
          })
        );

        const scheduleElements = schedules.map((schedule, index) => {
          const isStartDay = isSameDay(parseISO(schedule.startDay), parseISO(key));
          return (
            <div 
              className="scheduleTitle" 
              style={{ 
                backgroundColor: schedule.color, 
              }} 
              key={`${schedule.title}-${index}-${isStartDay}`}
            >
              {isStartDay && <p style={{ margin: 0 }}>{schedule.title}</p>}
            </div>
          );
        });

        weekList.push(
          <td className={classNames} key={key}>
            <span>{formattedDate}</span>
            <span className='dateToday'>{today === key ? "오늘" : null}</span>
            {scheduleElements}
          </td>
        );
        date = addDays(date, 1);
      }
      tempDateList.push(<tr key={tempDateList.length}>{weekList}</tr>);
    }
    setDateList(tempDateList); // 캘린더 그리기가 완료되면 dateList 업데이트
  };

  return <>{dateList}</>;
};

export default RenderDate