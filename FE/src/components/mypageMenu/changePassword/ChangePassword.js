import React, { useState } from "react";
import api from "../../../utils/api";
const ChangePassword = ({user, setUser}) => {
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [checkPassword, setCheckPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState(null);

  // user, setUser 받아와서 user의 비밀번호, 변경완료시 setUser(null);

  const onClickChangeBtn = async () => {
    try {
      if(!currentPassword){
        throw new Error("현재 비밀번호를 입력하세요.")
      }
      if(!newPassword){
        throw new Error("변경 비밀번호를 입력하세요.")
      }
      if(!checkPassword){
        throw new Error("비밀번호 확인을 입력하세요.")
      }
      if(newPassword !== checkPassword){
        throw new Error("변경과 확인 비밀번호가 다릅니다.");
      }
      const response = await api.patch('/home/change/password',{currentPassword,newPassword,checkPassword});
      if(response.status === 200){
        alert("비밀번호 변경완료. 다시 로그인 하세요.");
        setUser(null);
        // setUser(null) 을 하면 쿠키는 남아있는데, 이럼 메인페이지로 갔다가 getUser에 
        // 의해서 다시 자동으로 로그인 될 수도 있곘다 여차하면 쿠키 지우거나 재로그인 안하기
      } else {
        throw new Error(response.data);
      }
    } catch (error) {
     setErrorMessage(error.message); 
    }
  }

  return (
    <div className="changePasswordPageWrap">
      <div className="changePasswordPage">
        <div className="changePasswordHeader">비밀번호를 변경하세요.</div>
        <div className="changePasswordContainer">
          <div className="changePasswordInputWrap">
            <input
              className="changePasswordInput"
              placeholder="현재 비밀번호"
              value={currentPassword}
              onChange={(e)=>{setCurrentPassword(e.target.value)}}
            />
          </div>
          <div className="changePasswordInputWrap">
            <input
              className="changePasswordInput"
              placeholder="변경 비밀번호"
              value={newPassword}
              onChange={(e)=>{setNewPassword(e.target.value)}}
            />
          </div>
          <div className="changePasswordInputWrap">
            <input
              className="changePasswordInput"
              placeholder="비밀번호 확인"
              value={checkPassword}
              onChange={(e)=>{setCheckPassword(e.target.value)}}
            />
          </div>
          <div className="errorMessageWrap">
            {errorMessage}
          </div>
        </div>
        <div className="changePasswordBtnWrap">
          <button 
            onClick={onClickChangeBtn}
            className="changePasswordBtn">변경하기</button>
          <button className="changePasswordcancleBtn">취소하기</button>
        </div>
      </div>
    </div>
  );
};

export default ChangePassword;