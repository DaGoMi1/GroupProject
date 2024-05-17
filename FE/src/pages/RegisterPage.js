import React from 'react'
import { useState } from 'react';
import api from '../utils/api';

const LoginPage = () => {
  const [username, setUsername] = useState(''); // 학번
  const [password, setPassword] = useState(''); // 비번
  const [password2, setPassword2] = useState(''); // 비번확인
  const [email, setEmail] = useState(''); // 인증 이메일
  const [name, setName] = useState(''); // 사용할 이름 및 닉네임 
  const [number, setNumber] = useState(''); // 인증번호
  const [isDisable, setIsDisable] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');
  const fetchAuthCode = async (event) => {
    event.preventDefault();
    const response = await api.post('/home/send-email',{email});
    console.log(response);
  }

  const checkAuthCode = async (event) => {
    event.preventDefault();
    const response = await api.post('/home/check/authCode',{email, number});
    if(response.data){
      setIsDisable(false);
    }
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      if(!username){
        throw new Error("학번을 입력하세요");
      }
      if(!password){
        throw new Error("비밀번호를 입력하세요");
      }
      if(!password2){
        throw new Error("비밀번호 확인을 입력하세요");
      }
      if(!name){
        throw new Error("닉네임을 입력하세요");
      }
      if(password !== password2){
        throw new Error("비밀번호가 다릅니다.");
      }

      const response = await api.post('home/user',{username,password,password2,name,email});
      console.log(response);
    } catch (error) {
      setErrorMessage(error.message);
    }
  }

  return (
    <>
      <div className='page'>
        <h1 className='titleWrap'>회원가입</h1>
        <div className='login_area'>
          <input
            onChange={(e)=>{setEmail(e.target.value)}} 
            value={email} 
            type="text" 
            placeholder='이메일@kmou.ac.kr' />
          <button onClick={fetchAuthCode} className='loginButton'>인증번호 발송</button>
          <input
            onChange={(e)=>{setNumber(e.target.value)}} 
            value={number} 
            type="number" 
            placeholder='인증번호' />
          <button onClick={checkAuthCode} className='loginButton'>인증번호 확인</button>
          <input 
            onChange={(e)=>{setUsername(e.target.value)}} 
            value={username} 
            type="number" 
            placeholder='학번' />
          <input
            onChange={(e)=>{setPassword(e.target.value)}} 
            value={password} 
            type="text" 
            placeholder='비밀번호 입력' />
          <input
            onChange={(e)=>{setPassword2(e.target.value)}} 
            value={password2} 
            type="text" 
            placeholder='비밀번호 확인' />
            <input
              onChange={(e)=>{setName(e.target.value)}} 
              value={name} 
              type="text" 
              placeholder='닉네임' />
          <div className="errorMessageWrap">{errorMessage}</div>
          <button 
            onClick={handleSubmit} 
            className={`loginButton ${isDisable ? 'disabledBtn' : null}`} 
            disabled={isDisable}>
            완료
          </button>
        </div>
      </div>
    </>
  )
}

export default LoginPage