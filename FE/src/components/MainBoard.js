import NoticeBoard from './mainBoard/NoticeBoard'
import BoardBoard from './mainBoard/BoardBoard'
import CalendarBoard from './mainBoard/CalendarBoard'
import Links from './mainBoard/Links'
import Footer from './Footer'

const MainBoard = ({sectionRef, setChangeComponent}) => {
  
  return (
    <>
      <div className="wrap">
        <div className="container">
          <NoticeBoard ref={sectionRef.noticeBoard} setChangeComponent={setChangeComponent}/>
          <BoardBoard ref={sectionRef.boardBoard} setChangeComponent={setChangeComponent}/>
          <CalendarBoard ref={sectionRef.calendarBoard}/>
         
          <Links ref={sectionRef.linkBoard}/>
        </div>
      </div>

      <Footer/>
    </>
  )
}

export default MainBoard