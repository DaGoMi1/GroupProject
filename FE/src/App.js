import logo from './logo.svg';
import './App.css';
import MainPage from './pages/MainPage';
import {Route, Routes} from 'react-router-dom';
import LoginPage from './pages/LoginPage';

function App() {
  
  return (
    <>
      <Routes>
        <Route path='/loginPage' element={<LoginPage/>}/>
        <Route path='/' element={<MainPage/>}/>
      </Routes>
    </>
  );
}

export default App;
