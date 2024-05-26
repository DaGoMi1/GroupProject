import React, { useEffect, useState } from 'react';

const RenderTimetable = ({ scheduleList , scheduleMap, setScheduleMap}) => {
  const days = ['월', '화', '수', '목', '금'];
  const times = Array.from({ length: 9 }, (_, i) => i + 9);

  const generateRandomColor = () => {
    const red = Math.floor(Math.random() * 256);
    const green = Math.floor(Math.random() * 256);
    const blue = Math.floor(Math.random() * 256);
    return `rgb(${red},${green},${blue})`;
  };

  const renderTimetable = () => {
    const newScheduleMap = new Map();

    scheduleList.forEach((schedule) => {
      const { courseName, lectureTime } = schedule;
      const classroomArr = lectureTime.slice(5).split('');
      classroomArr.pop();
      const classroom = classroomArr.join('');
      const lectureDay = lectureTime[0]; // 수업 날짜
      const startTime = +lectureTime[1] + 8; // 시작 시간 : 9
      const endTime = +lectureTime[3] + 8; // 종료 시간 : 10

      const randomColor = generateRandomColor(); // 랜덤 색상 생성

      for (let time = startTime; time <= endTime; time++) {
        const key = `${lectureDay}-${time}`;
        const rowSpan = endTime - startTime + 1;
        newScheduleMap.set(key, { courseName, classroom, startTime, endTime, rowSpan, color: randomColor });
      }
    });

    setScheduleMap(newScheduleMap);
  };

  useEffect(() => {
    renderTimetable();
  }, [scheduleList]);

  return (
    <>
      <table>
        <thead>
          <tr>
            <th>시간</th>
            {days.map(day => <th key={day}>{day}</th>)}
          </tr>
        </thead>
        <tbody>
          {times.map(time => (
            <tr key={time}>
              <td className='time'>{`${time < 10 ? '0' + time : time}:00-${time + 1}:00`}</td>
              {days.map(day => {
                const key = `${day}-${time}`;
                const schedule = scheduleMap.get(key);

                if (schedule) {
                  if (time === schedule.startTime) {
                    return (
                      <td key={key} rowSpan={schedule.rowSpan} style={{color:"white", backgroundColor: schedule.color }}>
                        <p>{schedule.courseName}</p>
                        <p>{schedule.classroom}</p>
                      </td>
                    );
                  }
                  return null;
                } else {
                  return <td key={key}></td>;
                }
              })}
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
};

export default RenderTimetable;
