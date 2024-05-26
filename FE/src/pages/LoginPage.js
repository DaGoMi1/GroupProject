import React from "react";
import { useState } from "react";
import { Link, useNavigate, Navigate } from "react-router-dom";

const LoginPage = ({user, setUser}) => {

  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState('');


  const submitEmailAndPassword = async (e) => {
    e.preventDefault();
    try {
      if (!username) {
        throw new Error("학번을 입력하세요.");
      }
      if (!password) {
        throw new Error("비밀번호를 입력하세요.");
      }
  
      const formData = new FormData();
      formData.append('username', username);
      formData.append('password', password);
  
      const response = await fetch('http://localhost:8080/home/user/login', {
        method: 'POST',
        body: formData,
        credentials: 'include' // 인증 정보를 포함시키기 위해 include 설정
      });
  
      if (response.ok) {
        const data = await response.json(); // 응답 데이터를 JSON 형식으로 파싱
        setUser(data);
        navigate('/');
      } else {
        throw new Error("로그인에 실패했습니다.");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  }


  if(user){
    return <Navigate to='/'/>
  }
  return (
    <div className="pageStyle">
      <div className="page">
        <div className="titleWrap">서비스를 이용하려면 로그인하세요.</div>
        <div>
          <img src="/images/logo.png" />
        </div>
        
          <form onSubmit={submitEmailAndPassword} className="login_area">
            <input
              type="text"
              className="input"
              placeholder="학번"
              value={username}
              onChange={(e)=>{setUsername(e.target.value)}}
            />
            <input
              type="password"
              className="input"
              placeholder="비밀번호"
              value={password}
              onChange={(e)=>{setPassword(e.target.value)}}
            />
            <button onClick={submitEmailAndPassword} className="loginButton">
              로그인
            </button>
          </form>
          
          <div className="errorMessageWrap">
            {errorMessage}
          </div>


        <div className="searchPasswordWrap">
          <Link to="/registerPage" className="searchPasswordLink">
            회원가입
          </Link>
          <Link to="/searchPassword" className="searchPasswordLink">
            비밀번호 찾기
          </Link>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;