import React from 'react'
import { useState, useEffect } from "react";
import api from '../utils/api';

const SearchPassword = () => {
  const [email, setEmail] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [message, setMessage] = useState('');

  const submitEmail = async () => {
    try {
      const regex = /^[a-zA-Z0-9._%+-]+@kmou\.ac\.kr$/;
      if(!email){
        throw new Error("이메일을 입력하세요.");
      }
      if(!regex.test(email)){
        throw new Error("이메일 형식이 올바르지 않습니다.");
      }
      const response = await api.post('/home/send-email',{email});
      setMessage("이메일로 임시 비밀번호를 보냈습니다.");
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
              placeholder="가입한 이메일"
              value={email}
              onChange={(e)=>{setEmail(e.target.value)}}
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