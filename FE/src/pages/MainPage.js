import React,{useRef} from 'react'
import Navbar from '../components/Navbar'
import Carousel from '../components/Carousel'
import MainBoard from '../components/MainBoard'
import ScrolltoTop from '../components/ScrolltoTop'

const MainPage = ({user, setChangeComponent}) => {
  const sectionRef = {
    noticeBoard: useRef(null),
    boardBoard: useRef(null),
    calendarBoard: useRef(null),
    meetingBoard: useRef(null),
    linkBoard: useRef(null),
  }
  return (
    <>
      <Navbar user={user} sectionRef={sectionRef} setChangeComponent={setChangeComponent}/>
      <Carousel/>
      <MainBoard sectionRef={sectionRef} setChangeComponent={setChangeComponent}/>
      <ScrolltoTop/>
    </>
  )
}

export default MainPage