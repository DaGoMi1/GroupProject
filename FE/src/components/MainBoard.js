import React from 'react'
import NoticeBoard from './mainBoard/NoticeBoard'
import BoardBoard from './mainBoard/BoardBoard'
import CalendarBoard from './mainBoard/CalendarBoard'
import MeetingBoard from './mainBoard/MeetingBoard'
import Links from './mainBoard/Links'
import Footer from './Footer'

const MainBoard = () => {
  return (
    <>
      <div className="wrap">
        <div className="container">
          <NoticeBoard/>
          <BoardBoard/>
          <CalendarBoard/>
          <MeetingBoard/>
          <Links/>
        </div>
      </div>

      <Footer/>
    </>
  )
}

export default MainBoard