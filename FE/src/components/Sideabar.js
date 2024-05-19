import React, { useEffect } from 'react'
import { useState } from "react";
import { Link} from "react-router-dom";

const Sideabar = ({setIsLogin,sidebarOpen, setSidebarOpen}) => {
  
  const onClickLogout = () => {
    const isLogout = window.confirm("정말 로그아웃 하시겠습니까?");
    if(isLogout){
      setIsLogin(false);
    } else {
      setIsLogin(true);
    }
  }

  return (
    <div className={`sidebar ${sidebarOpen ? "active ": "hide"}`}>
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
        <Link to='/infoChange' className="change">회원 정보 수정</Link>
        <button onClick={onClickLogout} className="logout">로그아웃</button>
      </div>
      
      <hr />
      
      <div className="sidebarMenu">
            <Link to="/myPage" className="sidebarMenuLink">
              비밀번호 변경하기
            </Link>
          </div>
          <hr />
          <div className="sidebarMenu">
            <Link to="/myPage" className="sidebarMenuLink">
              캘린더 보러가기
            </Link>
          </div>
          <hr />
          <div className="sidebarMenu">
            <Link to="/myPage" className="sidebarMenuLink">
              시간표 보러가기
            </Link>
          </div>
          <hr />
          <div className="sidebarMenu">
            <Link to="/myPage" className="sidebarMenuLink">
              학점 계산기
            </Link>
          </div>
      
      <hr />
      
      <div className="logo">
        <img src="img/logo.png" />
      </div>
      
      <div className="question">문의 : sanginjeong07@gmail.com</div>
    </div>
    
  )
}

export default Sideabar