import React from 'react'
import { useState, useEffect } from "react";
import api from '../utils/api';
import { useNavigate } from 'react-router-dom';

const SearchPassword = () => {
  const [username, setUsername] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();
  const submitEmail = async () => {
    try {
      if(!username){
        throw new Error("학번을 입력하세요.");
      }
      if(username.length !== 8){
        throw new Error("학번이 올바르지 않습니다.");
      }
      const response = await api.post('/home/send/password',{username});
      setMessage("이메일로 임시 비밀번호를 보냈습니다.");
      navigate('/loginPage');
    } catch (error) {
      setErrorMessage(error.message);
    }
  }
  return (
    <div className='pageStyle'>
      <div className="page">
        <div className="titleWrap">비밀번호 찾기</div>
        <div>
          <img src="/img/logo.png" />
        </div>
        <div className="contentWrap">
          <div className="inputWrap">
            <input
              type="text"
              className="input"
              placeholder="학번"
              value={username}
              onChange={(e)=>{setUsername(e.target.value)}}
            />
          </div>
          {message ? <div className='messageWrap'>{message}</div> : <div className='errorMessageWrap'>{errorMessage}</div>}
          <div>
            <button onClick={submitEmail} className="searchPasswordButton">
              비밀번호 찾기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SearchPassword