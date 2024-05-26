import React, {useState} from 'react'
import NavbarWithoutUl from '../components/NavbarWithoutUl';
import Footer from '../components/Footer';
import InfoChange from '../components/mypageMenu/infoChange/InfoChange'
import ChangePassword from '../components/mypageMenu/changePassword/ChangePassword'
import Calculator from '../components/mypageMenu/calculator/Calculator'
import NoticeArea from '../components/mypageMenu/notice/NoticeArea'
import UserCalendarBoard from '../components/mypageMenu/userCalendar/UserCalendarBoard';
import Timetable from '../components/mypageMenu/timetable/Timetable';
import FreeArea from '../components/mypageMenu/board/FreeArea';

const MyPage = ({user, setUser, changeComponent, setChangeComponent}) => {
  return (
    <div>
      <NavbarWithoutUl 
        user = {user} 
        changeComponent={changeComponent} 
        setChangeComponent={setChangeComponent} />
      <div>
        <div className="myPageWrap">
          <div className="pageTitle">
            {changeComponent === "changePassword" && (
              <div> 비밀번호 변경하기</div>
            )}
            {changeComponent === "infoChange" && <div>회원 정보 수정</div>}
            {changeComponent === "calendar" && <div>캘린더 보러가기</div>}
            {changeComponent === "timeTable" && <div>시간표 보러가기</div>}
            {changeComponent === "calculator" && <div>학점 계산기</div>}
            {changeComponent === "notice" && <div>공지사항</div>}
            {changeComponent === "noticeContents" && <div>공지사항</div>}
            {changeComponent === "board" && <div>자유게시판</div>}
          </div>
          <div>
            <hr className="rowHr" />
          </div>

          <div className="myPageBoard">
            <div className="myPage">
              <div className="myPageBoardTitle">마이페이지</div>
              <button
                className={
                  changeComponent === "infoChange"
                    ? "thisPage"
                    : "myPageBoardMenu"
                }
                onClick={() => setChangeComponent("infoChange")}
              >
                회원 정보 수정
              </button>
              <button
                className={
                  changeComponent === "changePassword"
                    ? "thisPage"
                    : "myPageBoardMenu"
                }
                onClick={() => setChangeComponent("changePassword")}
              >
                비밀번호 변경하기
              </button>
              <button
                className={
                  changeComponent === "calendar"
                    ? "thisPage"
                    : "myPageBoardMenu"
                }
                onClick={() => setChangeComponent("calendar")}
              >
                캘린더 보러가기
              </button>
              <button
                className={
                  changeComponent === "timeTable"
                    ? "thisPage"
                    : "myPageBoardMenu"
                }
                onClick={() => setChangeComponent("timeTable")}
              >
                시간표 보러가기
              </button>
              <button
                className={
                  changeComponent === "calculator"
                    ? "thisPage"
                    : "myPageBoardMenu"
                }
                onClick={() => setChangeComponent("calculator")}
              >
                학점 계산기
              </button>
            </div>
              <div className="board">
                <div className="myPageBoardTitle">게시판</div>
                <button
                  className={
                    changeComponent === "notice"
                      ? "thisPage"
                      : "myPageBoardMenu"
                  }
                  onClick={() => setChangeComponent("notice")}
                >
                  공지사항
                </button>
                <button
                  className={
                    changeComponent === "board" ? "thisPage" : "myPageBoardMenu"
                  }
                  onClick={() => setChangeComponent("board")}
                >
                  자유게시판
                </button>
              </div>
            </div>
          <div className="myPageContents">
            {changeComponent === "infoChange" && <InfoChange />}
            {changeComponent === "changePassword" && <ChangePassword user={user} setUser={setUser}/>}
            {changeComponent === "calendar" && <UserCalendarBoard/>}
            {changeComponent === "timeTable" && <Timetable />}
            {changeComponent === "calculator" && <Calculator />}
            {changeComponent === "notice" && <NoticeArea user={user} />}
            {changeComponent === "board" && <FreeArea />}
          </div>
        </div>
      </div>
      <div className="myPageFooter">
        <Footer />
      </div>
    </div>
  );
}

export default MyPage