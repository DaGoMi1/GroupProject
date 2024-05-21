import React from "react";
import BoardTableItem from "./BoardTableItem";

const BoardTable = (props) => {
  return (
    <div>
      <table className="boardTable">
        <thead>
          <tr>
            <td className="boardTdIndex btIndex">구분</td>
            <td className="boardTdTitle btTitle">제목</td>
            <td className="boardTdWriter btWriter">작성자</td>
            <td className="boardTdDate btDate">날짜</td>
          </tr>
        </thead>
      </table>
      {props.board
        .map((title, index) => <BoardTableItem title={title} index={index} />)
        .reverse()}
    </div>
  );
};

export default BoardTable;