import React, { useState } from 'react'

const Notice = ({id,title,content,boardType,time,author, onClickPostBtn}) => {
  
  return (
    <tr>
      <td className="noticeTdNumber">{id}</td>
      <td className="noticeTdTitle notCenter">
          <button
            className='onClickPostBtn'
            onClick={()=>{onClickPostBtn(id)}}
          >{title}</button>
        </td>
      <td className="noticeTdWriter">{author}</td>
      <td className="noticeTdDate">{time}</td>
    </tr>
  )
}

export default Notice