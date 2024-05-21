import React, { useState, useEffect } from "react";


const BoardContents = (props) => {
  const [comment, setComment] = useState("");
  const [commentValid, setCommentValid] = useState("");
  const [notAllow, setNotAllow] = useState(true);
  const [commentList, setCommentList] = useState([]);

  const handleComment = (e) => {
    const commentInputValue = e.target.value;
    setComment(commentInputValue);

    const commentRegex = /^.{2,}$/;
    setCommentValid(commentRegex.test(commentInputValue));
  };

  const handleAddComment = () => {
    if (commentValid) {
      setCommentList([...commentList, comment]);
      setComment("");
      setNotAllow(true);
    }
  };

  useEffect(() => {
    setNotAllow(!commentValid);
  }, [commentValid]);

  return (
    <div>
      <table className="boardTable">
        <thead>
          <tr>
            <td className="boardTdTitle btTitle">제목</td>
            <td className="boardTdWriter btWriter">name</td>
            <td className="boardTdDate btDate">2024. 03. 16</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td colSpan="3">~~~</td>
          </tr>
          <tr>
            <td colSpan="3">
              댓글
              <input
                type="text"
                className="boardCommentInput"
                value={comment}
                onChange={handleComment}
              />
              <button
                disabled={notAllow}
                onChange={handleComment}
                className="boardCommentButton"
                onClick={handleAddComment}
              >
                등록
              </button>
            </td>
          </tr>
          {commentList.map((comm, index) => (
            <tr key={index}>
              <td>이름</td>
              <td colSpan="2">{comm}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BoardContents;