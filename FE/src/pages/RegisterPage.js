import React from 'react'
import { useState } from 'react';
import api from '../utils/api';

const LoginPage = () => {
  const [username, setUsername] = useState(''); // 학번
  const [password, setPassword] = useState(''); // 비번
  const [password2, setPassword2] = useState(''); // 비번확인
  const [email, setEmail] = useState(''); // 인증 이메일
  const [name, setName] = useState(''); // 사용할 이름 및 닉네임 

  const handleSubmit = async (event) => {
    event.preventDefault();
    
    const response = await api.post('/home/user',{username, password, password2, email, name});
    console.log(response);
  }

  return (
    <>
      <div className='page'>
        <h1 className='titleWrap'>회원가입</h1>
        <form onSubmit={handleSubmit} className='login_area'>
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
            onChange={(e)=>{setEmail(e.target.value)}} 
            value={email} 
            type="text" 
            placeholder='이메일@kmou.ac.kr' />
          <input
            onChange={(e)=>{setName(e.target.value)}} 
            value={name} 
            type="text" 
            placeholder='닉네임' />
          <button onClick={handleSubmit} className='loginButton'>인증번호 발송</button>
          <input
            onChange={(e)=>{setName(e.target.value)}} 
            value={name} 
            type="number" 
            placeholder='인증번호' />
          <button onClick={handleSubmit} className='loginButton'>완료</button>
        </form>
      </div>
    </>
  )
}

export default LoginPage