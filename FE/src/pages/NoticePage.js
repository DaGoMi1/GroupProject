import React from 'react'
import {Link} from 'react-router-dom'

const NoticePage = () => {
  return (
    <div>
    <div className="page1">
      <div>
        <div className="noticeTableWrap">
          <table className="noticeTable">
            <thead>
              <tr>
                <td className="noticeTableNumber">구분</td>
                <td className="noticeTableTitle">제목</td>
                <td className="noticeTableWriter">작성자</td>
                <td className="noticeTableDate">날짜</td>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="noticeTableNumber">1</td>
                <td className="noticeTableTitle notCenter">
                  <Link
                    to="/NoticeContents"
                    className="noticeTableTitleContents"
                  >
                    수강 신청 안내
                  </Link>
                </td>
                <td className="noticeTableWriter">학사과</td>
                <td className="noticeTableDate">2024. 03. 16</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
  )
}

export default NoticePage