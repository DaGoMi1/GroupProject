import React,{useRef} from 'react'
import Navbar from '../components/Navbar'
import Carousel from '../components/Carousel'
import MainBoard from '../components/MainBoard'
import ScrolltoTop from '../components/ScrolltoTop'

const MainPage = ({setIsLogin}) => {
  const sectionRef = {
    noticeBoard: useRef(null),
    boardBoard: useRef(null),
    calendarBoard: useRef(null),
    meetingBoard: useRef(null),
    linkBoard: useRef(null),
  }
  return (
    <>
      <Navbar setIsLogin={setIsLogin} sectionRef={sectionRef}/>
      <Carousel/>
      <MainBoard sectionRef={sectionRef}/>
      <ScrolltoTop/>
    </>
  )
}

export default MainPage