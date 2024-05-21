import React from "react";

const NoticeContents = () => {
  return (
    <div>
      <table className="noticeTable">
        <thead>
          <tr>
            <td className="noticeTdTitle ntTitle">수강 신청 안내</td>
            <td className="noticeTdWriter ntWriter">학사과</td>
            <td className="noticeTdDate ntDate">2024. 03. 16</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td colSpan="3">내용</td>
          </tr>
          <tr>
            <td>첨부파일</td>
            <td colSpan="2">pdf</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default NoticeContents;