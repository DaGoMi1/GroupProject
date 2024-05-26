import React from 'react'

const alertModal = ({setIsOpenAlertModal}) => {
  return (
    <div className='modal'>
      <div className='modalOverlay'></div>
      <div className="alertModal">
        <h2>시간대가 중복됩니다.</h2>
        <button
          className='onClickCheckBtn'
          onClick={()=>{setIsOpenAlertModal(false)}}>확인</button>
      </div>
    </div>
  )
}

export default alertModal