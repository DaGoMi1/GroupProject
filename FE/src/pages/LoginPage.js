import React from "react";
import { useState } from "react";
import { Link, useNavigate, Navigate } from "react-router-dom";
import api from "../utils/api";

const LoginPage = ({user, setUser}) => {

  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState('');


  const submitEmailAndPassword = async (e) => {
    e.preventDefault();
    try {
      if(!email){
        throw new Error("이메일을 입력하세요.");
      }
      if(!password){
        throw new Error("비밀번호를 입력하세요.");
      }

      const response = await api.post('/home/user/login', {email,password});
      if(response.status === 200){
        setUser(response.data.user);
        sessionStorage.setItem("token", response.data.token);
        api.defaults.headers["authorization"] = "Bearer " + response.data.token;
        navigate('/');
      } else if (response.status !== 200){
        throw new Error(response.error);
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
          <img src="/img/logo.png" />
        </div>
        
          <form onSubmit={submitEmailAndPassword} className="login_area">
            <input
              type="text"
              className="input"
              placeholder="학번"
              value={email}
              onChange={(e)=>{setEmail(e.target.value)}}
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