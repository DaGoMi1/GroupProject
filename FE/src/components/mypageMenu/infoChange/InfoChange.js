import React from "react";

const InfoChange = () => {
  return (
    <div className="infoChangePageWrap">
      <div className="infoChangePage">
        <div className="infoChangeHeader">회원 정보 수정</div>
        <div>
          <div className="infoChangeInputWrap">
            {/* <label className="infoChangeLabel">이름</label> */}
            <input className="infoChangeInput" placeholder="이름"/>
          </div>
          <div className="infoChangeInputWrap">
            {/* <label className="infoChangeLabel">전화번호</label> */}
            <input className="infoChangeInput" placeholder="전화번호" />
          </div>
          <div className="infoChangeInputWrap">
            {/* <label className="infoChangeLabel">이메일</label> */}
            <input className="infoChangeInput" placeholder="이메일"/>
          </div>
        </div>
        <div className="infoChangeBtnWrap">
          <button className="infoChangeBtn">변경하기</button>
          <button className="infoChangecancleBtn">취소하기</button>
        </div>
      </div>
    </div>
  );
};

export default InfoChange;