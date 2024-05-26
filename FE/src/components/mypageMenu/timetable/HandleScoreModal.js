import React, {useEffect, useState} from 'react'
import api from '../../../utils/api';

const HandleScoreModal = ({setIsOpenScoreModal, grade, semester, scheduleList, setScheduleList, getmyTimeTable}) => {
  const [scores, setScores] = useState(scheduleList.map(() => '')); // 각 과목에 대한 점수를 저장하는 상태
  const [errorMessage, setErrorMessage] = useState(null);
  const semesterObj = {
    'first': 1,
    'second': 2,
    'summer': '여름계절',
    'winter': '겨울계절',
  }
  const semesterName = semesterObj[semester];

  const handleScoreChange = (index, value) => {
    const newScores = [...scores];
    newScores[index] = value;
    setScores(newScores);
  }

  const saveSubjectScore = async () => {
    try {
      const updatedScheduleList = scheduleList.map((schedule, index) => (
        {...schedule, grade: scores[index]}
      ));
      setScheduleList(updatedScheduleList);

      // 각 과목의 id + 성적
      for (let i = 0; i < updatedScheduleList.length; i++) {
        const schedule = updatedScheduleList[i];
        const subjectId = schedule.id;
        const subjectGrade = schedule.grade;

        const response = await api.post('/gpa/credit', {subjectId, grade: subjectGrade});
        if (response.status !== 200) {
          throw new Error(response.data);
        }
      }
      getmyTimeTable(); // 컬러가 바뀌는 이유는 scheduleList가 변경될 때마다
      // 스케줄표를 재렌더링하는데 이 때 고유 컬러가 없고 랜덤으로 부여하기 때문
      setIsOpenScoreModal(false);

    } catch (error) {
      setErrorMessage(error.message);
    }
  }

  useEffect(() => {
    console.log(scores);
  }, [scores]);

  return (
    <div className='modal'>
      <div className='modalOverlay'></div>
      <div className="scoreModal">
        <h3>{grade}학년 {semesterName}학기</h3>
        {scheduleList.map((schedule, index) => (
          <div className='modalSubject_area' key={schedule.id}>
            <h4 style={{marginBottom: "13px", marginTop: "13px"}}>{schedule.courseName}</h4>
            <select
              name="학점"
              value={scores[index]}
              onChange={(e) => handleScoreChange(index, e.target.value)}
            >
              <option value=""></option>
              <option value="A+">A+</option>
              <option value="A0">A0</option>
              <option value="B+">B+</option>
              <option value="B0">B0</option>
              <option value="C+">C+</option>
              <option value="C0">C0</option>
              <option value="D+">D+</option>
              <option value="D0">D0</option>
              <option value="F">F</option>
              <option value="P">P</option>
            </select>
          </div>
        ))}
        <div className="errorMessageWrap">{errorMessage}</div>
        <div style={{display: 'flex', justifyContent: "center"}}>
          <button
            onClick={saveSubjectScore}
            className='completeBtn'>저장
          </button>
          <button
            className='cancelBtn'
            onClick={() => setIsOpenScoreModal(false)}>취소
          </button>
        </div>
      </div>
    </div>
  )
}

export default HandleScoreModal;