import React from 'react'

const Subject = ({subject,onClickAddSubjectBtn}) => {
  return (
    <>
      <div className='subject'>
        <div className='subjectInfo'>
          <h4>{subject.courseName}</h4>
          <h5>{subject.professor}</h5>
          <h6>{subject.lectureTime}</h6>
          <div>
            <h6>{subject.subjectYear}학년</h6>
            <h6>{subject.area}</h6>
            <h6>{subject.credit}학점</h6>
            <h6>{subject.courseCode}</h6>
          </div>
        </div>
        <div className='addSubjectBtn_area'>
          <button
            onClick={()=>{onClickAddSubjectBtn(subject.id)}} 
            className='addSubjectBtn'>추가하기</button>
        </div>
      </div>
    </>
  )
}

export default Subject