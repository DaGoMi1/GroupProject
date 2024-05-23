import React from 'react'
import Lecture from './Lecture';

const RenderTimetable = ({scheduleList}) => {
  const days = ['월', '화', '수', '목', '금'];
  const times = Array.from({ length: 9 }, (_, i) => i + 9)

  console.log(days, times,scheduleList);
  return (
    <>
      <table>
        <thead>
          <th>시간</th>
          {days.map((day=><th key={day}>{day}</th>))}
        </thead>

        <tbody>
          {times.map((time)=>(
            <tr key={time}>
              <td className='time'>{`${time<10 ? '0'+time : time}:00-${time+1}:00`}</td>
              {days.map((day)=>(
                // 스케줄리스트 돌면서 날짜와 시작시간,종료시간 선언
                // 월요일 9시부터 화요일 9시 이런식으로 순서대로 검사함
                // 같다면 td에 채우기 다르다면 그냥 td
                scheduleList.forEach((schedule,index)=>{
                  const {courseName, lectureTime} = schedule;
                  const classroomArr = lectureTime.split('').slice(5);
                  classroomArr.pop();
                  const classroom = classroomArr.join(''); // 강의실  
                  const lectureDay = lectureTime[0] // 수업 날짜
                  const startTime = lectureTime[1]; // 시작 교시
                  const endTime = lectureTime[3]; // 종료 교시
                  console.log(classroom,lectureDay,startTime,endTime);
                })
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}

export default RenderTimetable