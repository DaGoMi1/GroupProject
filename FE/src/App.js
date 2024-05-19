import logo from './logo.svg';
import './App.css';
import MainPage from './pages/MainPage';
import {Route, Routes, Navigate} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import { useState, useEffect } from 'react';
import NoticePage from './pages/NoticePage';
import SearchPassword from './pages/SearchPassword';
import MyPage from './pages/MyPage';
import api from './utils/api';
import PrivatePage from './route/PrivatePage';

function App() {
  const [user, setUser] = useState(null);

  const getUser = async() => {
    try {
      const storedToken = sessionStorage.getItem("token");
      if(storedToken){
        const response = await api.get("/home/name");
        setUser(response.data.user);
        console.log('user',user);
      }
    } catch (error) {
      setUser(null);
    }
  }
  useEffect(()=>{getUser()},[]);
  useEffect(()=>{console.log('user:',user);},[user]);
  return (
    <>
      <Routes>
        <Route path ='/loginPage' element={<LoginPage user = {user} setUser={setUser}/>}/>
        <Route path ='/searchPassword' element={<SearchPassword/>}/>
        <Route path ='/' element={<PrivatePage user={user}>
          <MainPage/>
        </PrivatePage>}/>
        <Route path ='/registerPage' element={<RegisterPage/>}/>
        <Route path='/noticePage' element={<NoticePage/>}/>
        <Route path='/mypage' element={<MyPage/>}/>
      </Routes>
    </>
  );
}

export default App;
