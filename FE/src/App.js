import logo from './logo.svg';
import './App.css';
import MainPage from './pages/MainPage';
import {Route, Routes, Navigate} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import { useState, useEffect } from 'react';
import SearchPassword from './pages/SearchPassword';
import MyPage from './pages/MyPage';
import api from './utils/api';
import PrivatePage from './route/PrivatePage';

  function App() {
    const [user, setUser] = useState('정상인');
    const [changeComponent, setChangeComponent] = useState("infoChange");
    
    const getUser = async() => {
      try {
        const response = await api.get("/home/name");
        if(response.status === 200){
          setUser(response.data);
        } else {
          setUser(null);
          throw new Error(response.data);
        }
      } catch (error) {
        console.log("response.data : ", error.message);
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
            <MainPage user={user} setChangeComponent={setChangeComponent}/>
          </PrivatePage>}/>
          <Route path='/myPage' element={<PrivatePage user={user}>
            <MyPage user={user} setUser={setUser} changeComponent={changeComponent} setChangeComponent={setChangeComponent}/>
          </PrivatePage>}/>
          <Route path ='/registerPage' element={<RegisterPage/>}/>

        </Routes>
      </>
    );
  }

  export default App;
