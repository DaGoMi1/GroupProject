import React from "react";
import { useState, useEffect } from "react";
import BoardTable from "./BoardTable";

const Board = () => {
  const [boardWriteFrame, setBoardWriteFrame] = useState(false);

  const [boardTitle, setBoardTitle] = useState("");
  const [boardContents, setBoardContents] = useState("");

  const [boardTitleValid, setBoardTitleValid] = useState(false);
  const [boardContentsValid, setBoardContentsValid] = useState(false);

  const [notAllow, setNotAllow] = useState(true);

  const onChange = (event) => {
    const { name, value } = event.target;

    if (name === "boardTitle") {
      setBoardTitle(value);
      const boardTitleRegex = /^.{2,}$/;
      setBoardTitleValid(boardTitleRegex.test(value));
    } else if (name === "boardContents") {
      setBoardContents(value);
      const boardContentsRegex = /^.{4,}$/;
      setBoardContentsValid(boardContentsRegex.test(value));
    }
  };

  useEffect(() => {
    if (boardTitleValid && boardContentsValid) {
      setNotAllow(false);
      return;
    }
    setNotAllow(true);
  }, [boardTitleValid, boardContentsValid]);

  const [board, setBoard] = useState([]);

  const handleRegisterButton = () => {
    setBoard([...board, boardTitle]);

    setBoardTitle("");
    setBoardContents("");

    setBoardWriteFrame(false);
  };

  return (
    <>
      <div className="boardPageWrap">
        <div className="boardPage">
          <div className="boardPage1">
            <div>
              <div className="boardWriteButtonWrap">
                <button
                  className="boardWriteButton"
                  onClick={() => {
                    setBoardWriteFrame((boardWriteFrame) => !boardWriteFrame);
                  }}
                >
                  글쓰기
                </button>
              </div>
            </div>
            <div className="boardTableWrap">
              <BoardTable board={board} />
            </div>
          </div>
          <div className="boardPage2">
            <div
              className={
                boardWriteFrame ? "boardWriteFrame active" : "boardWriteFrame"
              }
            >
              <div className="boardWriteWrap">
                <div>
                  <button
                    onClick={() => {
                      setBoardWriteFrame((boardWriteFrame) => !boardWriteFrame);
                    }}
                    className="boardWriteXButton"
                  >
                    X
                  </button>
                </div>
                <div className="boardWrite">
                  <div>
                    <label className="boardWriteLabel">제목</label>
                    <input
                      type="text"
                      name="boardTitle"
                      className="boardWriteTitle"
                      value={boardTitle}
                      onChange={onChange}
                    />
                  </div>
                  <div>
                    <label className="boardWriteLabel">내용</label>
                    <input
                      type="text"
                      name="boardContents"
                      className="boardWriteContents"
                      value={boardContents}
                      onChange={onChange}
                    />
                  </div>
                  <div>
                    <button
                      disabled={notAllow}
                      className="boardRegisterButton"
                      onClick={handleRegisterButton}
                    >
                      등록
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Board;