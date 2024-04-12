import logo from './logo.svg';
import './App.css';
import MainPage from './pages/MainPage';
import {Route, Routes, Navigate} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import { useState } from 'react';
function App() {
  const [isLogin, setIsLogin] = useState(false);
  
  const PrivatePage = () => {
    return isLogin ? <MainPage setIsLogin={setIsLogin}/> : <Navigate to = '/loginPage'/>;
  }
  
  return (
    <>
      <Routes>
        <Route path='/loginPage' element={<LoginPage/>}/>
        <Route path='/' element={<PrivatePage/>}/>
      </Routes>
    </>
  );
}

export default App;
