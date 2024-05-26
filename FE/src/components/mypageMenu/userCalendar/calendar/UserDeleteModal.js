import React from 'react';

const UserDeleteModal = ({ scheduleId, onClickCompleteDeleteBtn, onClickCancelDeleteBtn }) => {
  const handleDelete = () => {
    onClickCompleteDeleteBtn(scheduleId);
  };

  return (
    <div className="modalBackground">
      <div className="modalContainer">
        <div className="titleCloseBtn">
          <button onClick={onClickCancelDeleteBtn}>X</button>
        </div>
        <div className="title">
          <h1>일정 삭제</h1>
        </div>
        <div className="body">
          <p>정말로 이 일정을 삭제하시겠습니까?</p>
        </div>
        <div className="footer">
          <button onClick={handleDelete}>삭제</button>
          <button onClick={onClickCancelDeleteBtn}>취소</button>
        </div>
      </div>
    </div>
  );
};

export default UserDeleteModal;
