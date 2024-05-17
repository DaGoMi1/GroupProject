import logo from './logo.svg';
import './App.css';
import MainPage from './pages/MainPage';
import {Route, Routes, Navigate} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import { useState, useEffect } from 'react';
import NoticePage from './pages/NoticePage';
function App() {
  // false로 바꿔야함 이따가
  const [isLogin, setIsLogin] = useState(true);
  const PrivatePage = () => {
    return isLogin ? <MainPage setIsLogin={setIsLogin}/> : <Navigate to = '/loginPage'/>;
  }
  return (
    <>
      <Routes>
        <Route path ='/loginPage' element={<LoginPage setIsLogin={setIsLogin}/>}/>
        <Route path ='/' element={<PrivatePage/>}/>
        <Route path ='/registerPage' element={<RegisterPage/>}/>
        <Route path='/noticePage' element={<NoticePage/>}/>
      </Routes>
    </>
  );
}

export default App;
