import React, {useState, useEffect} from 'react'
import api from '../../../utils/api';
import Subject from './Subject';
import RenderTimeTable from './RenderTimeTable';
import HandleScoreModal from './HandleScoreModal';
import AlertModal from './AlertModal';
import SubjectList from './SubjectList';
const Timetable = () => {
  // 평점 저장 요청도 해야함
  const [grade, setGrade] = useState(1);
  const [semester, setSemester] = useState('first');
  const [errorMessage, setErrorMessage] = useState('');
  const [year, setYear] = useState(2024);
  const [classSemester, setClassSemester] = useState('first');
  const [curriculumType, setCurriculumType] = useState('전공');
  
  const [scheduleMap, setScheduleMap] = useState(new Map());
  const [scheduleList, setScheduleList] = useState([]);
  const [subjectList, setSubjectList] = useState(null);
  const [hidden, setHidden] = useState('hidden');
  const [isOpenScoreModal, setIsOpenScoreModal] = useState(false);
  const [isOpenAlertModal, setIsOpenAlertModal] = useState(false);
  const getmyTimeTable = async () => {
    try {
      const response = await api.get(`/time-table/member?grade=${grade}&semester=${semester}`);
      if(response.status === 200){
        setScheduleList(response.data);
            setHidden('');
      } else {
        throw new Error(response.data);
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  }

  const getClassList = async () => {
    try {
      const semester = classSemester;
      const response = await api.get(`/time-table/subject?
        year=${year}&
        semester=${semester}&
        curriculumType=${curriculumType}`)
      setSubjectList(response.data);
      
    } catch (error) {
      setErrorMessage(error.message)
    }
  }

  const onClickAddSubjectBtn = async (id, time) => {
    try {
      const subjectId = id;
      const lectureTime = time;
      const lectureDay = lectureTime[0];
      const startTime = +lectureTime[1] + 8; 
      const endTime = +lectureTime[3] + 8;

      let hasConflict = false;

      for (let time = startTime; time <= endTime; time++) {
        const key = `${lectureDay}-${time}`;
        if (scheduleMap.has(key)) {
          hasConflict = true;
          break;
        }
      }

      if(hasConflict) {
        setIsOpenAlertModal(true);
        return
      }
      const response = await api.post('/time-table/subject',{grade,semester,subjectId})
        if(response.status === 200){
        getmyTimeTable(); 
      } else {
        throw new Error(response.data);
      }
    } catch (error) {
      setErrorMessage(error.message)
    }
  }

  const onClickHandleScoreBtn = () => {
    setIsOpenScoreModal(true);
  }
  return (
    <>
      {isOpenAlertModal
        ? <AlertModal setIsOpenAlertModal={setIsOpenAlertModal}/>
        : null
      }
      {isOpenScoreModal 
        ? <HandleScoreModal 
            setIsOpenScoreModal={setIsOpenScoreModal}
            grade={grade}
            semester={semester}
            scheduleList={scheduleList}
            setScheduleList={setScheduleList}
            getmyTimeTable={getmyTimeTable}
          /> 
        : null}
      <div className='timeTableContainer'>
        <div className="getTimeTableWrap">
          <h3>시간표 불러오기</h3>
          <span>학년 : </span>
          <select name="학년" value={grade} onChange={(e)=>{setGrade(e.target.value)}}>
            <option value={1}>1</option>
            <option value={2}>2</option>
            <option value={3}>3</option>
            <option value={4}>4</option>
          </select>
          <span>학기 : </span>
          <select name="학기" value={semester} onChange={(e)=>{setSemester(e.target.value)}}>
            <option value="first">1</option>
            <option value="summer">여름계절</option>
            <option value="second">2</option>
            <option value="winter">겨울계절</option>
          </select>

          <button 
            onClick={getmyTimeTable}
            className='getTimeTableBtn'>불러오기
          </button>
          
          <span className='errorMessageWrap'>{errorMessage}</span>
          <button onClick={onClickHandleScoreBtn} className={`handleScoreBtn ${hidden}`}>성적보기 / 수정</button>

          <div className='timetable_area'>
            <div className="timeTableWrap">
              <RenderTimeTable scheduleList={scheduleList} scheduleMap={scheduleMap} setScheduleMap={setScheduleMap}/>
            </div>

            <div className='timetable_right_area'>
              <div>
                <h3>수강과목리스트 불러오기</h3>
                <select name="년도" value={year} onChange={(e)=>{setYear(e.target.value)}}>
                  <option value={2021}>2021</option>
                  <option value={2022}>2022</option>
                  <option value={2023}>2023</option>
                  <option value={2024}>2024</option>
                </select>
                <select name="학기" value={classSemester} onChange={(e)=>{setClassSemester(e.target.value)}}>
                  <option value="first">1</option>
                  <option value="summer">여름계절</option>
                  <option value="second">2</option>
                  <option value="winter">겨울계절</option>
                </select>
                <select name="과목구분" value={curriculumType} onChange={(e)=>{setCurriculumType(e.target.value)}}>
                  <option value="전공">전공</option>  
                  <option value="교양">교양</option>  
                </select>              
                <button 
                  onClick={getClassList}
                  className='getTimeTableBtn'>불러오기
                </button>
              </div>
              
              {subjectList
                ? <SubjectList
                    subjectList={subjectList}
                    onClickAddSubjectBtn={onClickAddSubjectBtn}
                  />
                : null
              }
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default Timetable