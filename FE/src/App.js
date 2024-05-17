import logo from './logo.svg';
import './App.css';
import MainPage from './pages/MainPage';
import {Route, Routes, Navigate} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import { useState, useEffect } from 'react';
import NoticePage from './pages/NoticePage';
function App() {
  const [isLogin, setIsLogin] = useState(false);
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
