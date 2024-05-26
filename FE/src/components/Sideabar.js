import React, { useEffect } from 'react'
import { useState } from "react";
import { Link} from "react-router-dom";

const Sideabar = ({user,sidebarOpen, setSidebarOpen, setChangeComponent}) => {
  const onClickLogout = () => {
    const isLogout = window.confirm("정말 로그아웃 하시겠습니까?");
    if(isLogout){
      const response = fetch('http://localhost:8080/home/user', {
        method: 'POST',
        credentials: 'include' // 인증 정보를 포함시키기 위해 include 설정
      })
      .then(response => {
        if (response.ok) {
          // 로그아웃 성공 시 필요한 후속 작업 수행
          // 예: 사용자 상태 업데이트, 리다이렉션 등
          setSidebarOpen(false);
          setChangeComponent(null);
          // 리다이렉트가 필요하다면 window.location.href를 사용
          window.location.href = "/LoginPage";
        }})
    } else {
      // setIsLogin(true);
    }
  }

  return (
    <div className={`sidebar ${sidebarOpen ? "active ": "hide"}`}>
      <button className='closeBtn' onClick = {()=>{setSidebarOpen(!sidebarOpen)}}>X</button>
      <hr />

      <div className="userInfo">
        <div className="photoWrap">
          <img className="photo" src="images/profile.png" />
        </div>
        
        <div className="nameStudentNumMajor">
          <div className="nameStudentNum">
            <div className="name">{user?.name}</div>
            <div className="studentNum">{user?.username}</div>
          </div>
          
          <div className="major">데이터사이언스전공</div>
        </div>
      </div>

      <div className="changeLogout">
        <Link to='/myPage' className="change">
          <button onClick={()=>{setChangeComponent("infoChange")}}>회원 정보 수정</button>
          </Link>
        <button onClick={onClickLogout} className="logout">로그아웃</button>
      </div>
      
      <hr />
      
      <div className="sidebarMenu">
            <Link to="/myPage" className="sidebarMenuLink">
              <button onClick={()=>{setChangeComponent("changePassword")}}>비밀번호 변경하기</button>
            </Link>
          </div>
          <hr />
          <div className="sidebarMenu">
            <Link to="/myPage" className="sidebarMenuLink">
              <button onClick={()=>{setChangeComponent("calendar")}}>캘린더 보러가기</button>
            </Link>
          </div>
          <hr />
          <div className="sidebarMenu">
            <Link to="/myPage" className="sidebarMenuLink">
              <button onClick={()=>{setChangeComponent("timeTable")}}>시간표 보러가기</button>
            </Link>
          </div>
          <hr />
          <div className="sidebarMenu">
            <Link to="/myPage" className="sidebarMenuLink">
              <button onClick={()=>{setChangeComponent("calculator")}}>학점 계산기</button>
            </Link>
          </div>
      
      <hr />
      
      <div className="question">문의 : sanginjeong07@gmail.com</div>
    </div>
    
  )
}

export default Sideabar