import React from 'react'
import { useState } from "react";
import { Routes, Route } from "react-router-dom";

const Sideabar = ({sidebarOpen, setSidebarOpen}) => {


  return (
    <div className="page">
      <div className='sidebar'>
        <div className={sidebarOpen ? "active" : "hide"}>

        <button className='closeBtn' onClick = {()=>{setSidebarOpen(!sidebarOpen)}}>X</button>
        <hr />
        <div className="userInfo">
          <div className="photoWrap">
            <img className="photo" src="img/증사아이콘.jpeg" />
          </div>
          <div className="nameStudentNumMajor">
            <div className="nameStudentNum">
              <div className="name">name</div>
              <div className="studentNum">12345678</div>
            </div>
            <div className="major">데이터사이언스전공</div>
          </div>
        </div>

        <div className="changeLogout">
          <div className="change">회원 정보 수정</div>
          <div className="logout">로그아웃</div>
        </div>
        <hr />
        <div className="sidebarMenu">비밀번호 변경하기</div>
        <hr />
        <div className="sidebarMenu">캘린더 보러가기</div>
        <hr />
        <div className="sidebarMenu">시간표 보러가기</div>
        <hr />
        <div className="sidebarMenu">학점 계산기</div>
        <hr />
        <div className="logo">
          <img src="img/logo.png" />
        </div>
        <div className="question">문의 : sanginjeong07@gmail.com</div>
      </div>
    </div>
      <Routes>
        <Route path="/infoChange">
          {/* element={< />} */}
          회원 정보 수정
        </Route>
        <Route path="/Logout">
          {/* element={< />} */}
          로그아웃
        </Route>
        <Route path="/SearchPassword">
          {/* element={< />} */}
          비밀번호 변경
        </Route>
        <Route path="/Calendar">
          {/* element={< />} */}
          캘린더 보러가기
        </Route>
        <Route path="/Titetable">
          {/* element={< />} */}
          시간표 보러가기
        </Route>
        <Route path="/Calculator">
          {/* element={< />} */}
          학점계산기
        </Route>
      </Routes>
    </div>
  )
}

export default Sideabar