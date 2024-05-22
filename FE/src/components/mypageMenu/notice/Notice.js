import React from "react";
import { Link } from "react-router-dom";

const Notice = () => {
  return (
    <div>
      <div className="noticePageWrap">
        <table className="noticeTable">
          <thead>
            <tr>
              <td className="noticeTdIndex ntIndex">구분</td>
              <td className="noticeTdTitle ntTitle">제목</td>
              <td className="noticeTdWriter ntWriter">작성자</td>
              <td className="noticeTdDate ntDate">날짜</td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td className="noticeTdNumber">1</td>
              <td className="noticeTdTitle notCenter">
                <Link to="/myPage" className="noticeTdTitleContents">
                  수강 신청 안내
                </Link>
              </td>
              <td className="noticeTdWriter">학사과</td>
              <td className="noticeTdDate">2024. 03. 16</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Notice;