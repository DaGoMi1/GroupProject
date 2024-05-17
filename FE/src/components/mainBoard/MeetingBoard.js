import React from 'react'

const MeetingBoard = React.forwardRef((props,ref) => {
  return (
    <div className='contents'>
      <div className="box category" ref={ref}>회의</div>
      <div className="box meet_area">
        <div>
          <h3 className='meet_top_mention'>조별 과제, 회의</h3>
          <h3 className='meet_bottom_mention'>비대면으로 해결하자</h3>
        </div>

        <div className='meet_btns_area'>
          <div>
            <button className='make_meetingroom'>회의방 만들기</button>
          </div>
          <div>
            <button className='join_meetingroom'>링크로 참여하기</button>
          </div>
        </div>
      </div>
    </div>
  )
})

export default MeetingBoard