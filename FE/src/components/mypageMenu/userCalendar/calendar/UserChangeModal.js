import React, { useState } from 'react';

const UserChangeModal = ({ personalSchedule, onClickCompleteChangeBtn, onClickCancelChangeBtn }) => {
  const [updatedSchedule, setUpdatedSchedule] = useState(personalSchedule);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedSchedule((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = () => {
    onClickCompleteChangeBtn(updatedSchedule);
  };

  return (
    <div className="modalBackground">
      <div className="modalContainer">
        <div className="titleCloseBtn">
          <button onClick={onClickCancelChangeBtn}>X</button>
        </div>
        <div className="title">
          <h1>일정 변경</h1>
        </div>
        <div className="body">
          <input
            type="text"
            name="title"
            value={updatedSchedule.title}
            onChange={handleChange}
            placeholder="일정 제목"
          />
          <input
            type="date"
            name="startDay"
            value={updatedSchedule.startDay}
            onChange={handleChange}
            placeholder="시작 날짜"
          />
          <input
            type="date"
            name="endDay"
            value={updatedSchedule.endDay}
            onChange={handleChange}
            placeholder="종료 날짜"
          />
          <input
            type="color"
            name="color"
            value={updatedSchedule.color}
            onChange={handleChange}
            placeholder="색상"
          />
        </div>
        <div className="footer">
          <button onClick={handleSubmit}>완료</button>
          <button onClick={onClickCancelChangeBtn}>취소</button>
        </div>
      </div>
    </div>
  );
};

export default UserChangeModal;
