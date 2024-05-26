import React, {useState, useEffect} from 'react'
import api from '../../../utils/api';
import Subject from './Subject';
import RenderTimeTable from './RenderTimeTable';

const Timetable = () => {
  const [grade, setGrade] = useState('1');
  const [semester, setSemester] = useState('first');
  const [errorMessage, setErrorMessage] = useState('');
  const [year, setYear] = useState('2024');
  const [classSemester, setClassSemester] = useState('first');
  const [curriculumType, setCurriculumType] = useState('전공');
  

  const [scheduleList, setScheduleList] = useState([]);
  const [subjectList, setSubjectList] = useState(null);

  const getmyTimeTable = async () => {
    // try {
    //   const response = await api.get('/time-table/member/list',{grade, semester});
    //   if(response.status === 200){
    //     setScheduleList(response.data);
    //   } else {
    //     throw new Error(response.data);
    //   }
    // } catch (error) {
    //   setErrorMessage(error.message);
    // }
  }

  const getClassList = async () => {
    setSubjectList([
      {
        "id": 3496,
        "area" : "기초교양",
        "professor" : "오영이",
        "courseCode" : "L0002",
        "courseName" : "대학생을위한글쓰기",
        "subjectYear" : "1",
        "credit" : "2",
        "lectureTime" : "월1~2(해사대학관-0107)"
      },
      {
        "id": 3500,
        "area" : "전공필수",
        "professor" : "이다검",
        "courseCode" : "L0003",
        "courseName" : "이다검의 당구레슨",
        "subjectYear" : "2",
        "credit" : "2",
        "lectureTime" : "화1~2(공대1호관-0107)"
      },
      {
        "id": 3500,
        "area" : "전공필수",
        "professor" : "이다검",
        "courseCode" : "L0003",
        "courseName" : "이다검의 당구레슨",
        "subjectYear" : "2",
        "credit" : "2",
        "lectureTime" : "화1~2(공대1호관-0107)"
      },
      {
        "id": 3500,
        "area" : "전공필수",
        "professor" : "이다검",
        "courseCode" : "L0003",
        "courseName" : "이다검의 당구레슨",
        "subjectYear" : "2",
        "credit" : "2",
        "lectureTime" : "화1~2(공대1호관-0107)"
      },
      {
        "id": 3500,
        "area" : "전공필수",
        "professor" : "이다검",
        "courseCode" : "L0003",
        "courseName" : "이다검의 당구레슨",
        "subjectYear" : "2",
        "credit" : "2",
        "lectureTime" : "화1~2(공대1호관-0107)"
      },
      {
        "id": 3500,
        "area" : "전공필수",
        "professor" : "이다검",
        "courseCode" : "L0003",
        "courseName" : "이다검의 당구레슨",
        "subjectYear" : "2",
        "credit" : "2",
        "lectureTime" : "화1~2(공대1호관-0107)"
      }
    ])
    // try {
    //   const semester = classSemester;
    //   const response = await api.get(`/time-table/subject/list?
    //     year=${year}&
    //     semester=${semester}&
    //     curriculumType=${curriculumType}`)
    // } catch (error) {
      
    // }
  }

  const onClickAddSubjectBtn = async (id) => {
    setScheduleList([
      {
        "id": 3496,
        "area" : "기초교양",
        "professor" : "오영이",
        "courseCode" : "L0002",
        "courseName" : "대학생을위한글쓰기",
        "subjectYear" : "1",
        "credit" : "2",
        "lectureTime" : "월1~2(해사대학관-0107)"
      },
      {
        "id": 3500,
        "area" : "전공필수",
        "professor" : "이다검",
        "courseCode" : "L0003",
        "courseName" : "이다검의 당구레슨",
        "subjectYear" : "2",
        "credit" : "2",
        "lectureTime" : "화1~2(공대1호관-0107)"
      },
    ])

    // 위는 테스트 코드 아래는 완성된 코드
    
    // const subjectId = id;
    // try {
    //   const response = await api.post('/time-table/subject/save',{grade,semester,subjectId})
    //   if(response.status === 200){
    //     getmyTimeTable(); 
    //   } else {
    //     throw new Error(response.data);
    //   }
    // } catch (error) {
    //   console.log(error.message);
    // }
  }


  return (
    <>
      <div className='timeTableContainer'>
        <div className="getTimeTableWrap">
          <h3>시간표 불러오기</h3>
          <span>학년 : </span>
          <select name="학년" value={grade} onChange={(e)=>{setGrade(e.target.value)}}>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
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

          <div className='timetable_area'>
            <div className="timeTableWrap">
              <RenderTimeTable scheduleList={scheduleList}/>
            </div>

            <div className='timetable_right_area'>
              <div>
                <h3>수강과목리스트 불러오기</h3>
                <select name="년도" value={year} onChange={(e)=>{setYear(e.target.value)}}>
                  <option value="2019">2019</option>
                  <option value="2019">2020</option>
                  <option value="2019">2021</option>
                  <option value="2019">2022</option>
                  <option value="2019">2023</option>
                  <option value="2019">2024</option>
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
                ? <div className='subject_area'>
                    {subjectList.map((subject)=>{
                      return <Subject subject={subject} onClickAddSubjectBtn={onClickAddSubjectBtn}/>
                    })}
                  </div> 
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