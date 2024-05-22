import React from "react";

const Calculator = () => {
  return (
    <div>
      <div className="calculatorWrap">
        <table className="CalculatorTable1">
          <thead>
            <tr>
              <th colSpan="2" className="calculatorThIndex ctIndex">
                구분
              </th>
              <th className="calculatorThGrade ctGrade">학점</th>
              <th className="calculatorThRemain ctRemain">잔여</th>
              <th className="calculatorThExcess ctExcess">초과</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td rowSpan="3">전공</td>
              <td>기초</td>
              <td>20 / 20</td>
              <td>-</td>
              <td>-</td>
            </tr>
            <tr>
              <td>필수</td>
              <td>42 / 46</td>
              <td>4</td>
              <td>-</td>
            </tr>
            <tr>
              <td>선택</td>
              <td>27 / 24</td>
              <td>-</td>
              <td>3</td>
            </tr>
            <tr>
              <td rowSpan="2">교양</td>
              <td>필수</td>
              <td>24 / 24</td>
              <td>-</td>
              <td>-</td>
            </tr>
            <tr>
              <td>선택</td>
              <td colSpan="3">
                <table className="CalculatorTable2">
                  <thead>
                    <tr>
                      <td>소영역</td>
                      <td>학점</td>
                      <td>잔여</td>
                      <td>초과</td>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>글로벌의사소통</td>
                      <td>2 / 2</td>
                      <td>-</td>
                      <td>-</td>
                    </tr>
                    <tr>
                      <td>글로벌의사소통</td>
                      <td>2 / 2</td>
                      <td>-</td>
                      <td>-</td>
                    </tr>
                    <tr>
                      <td>해양문화</td>
                      <td>4 / 2</td>
                      <td>-</td>
                      <td>2</td>
                    </tr>
                    <tr>
                      <td>해양스포츠와예술</td>
                      <td>0 / 2</td>
                      <td>2</td>
                      <td>-</td>
                    </tr>
                    <tr>
                      <td>역사와문화</td>
                      <td>4 / (4/5)</td>
                      <td>-</td>
                      <td>-</td>
                    </tr>
                    <tr>
                      <td>사회와경제</td>
                      <td>6 / (4/5)</td>
                      <td>-</td>
                      <td>1</td>
                    </tr>
                    <tr>
                      <td>인성</td>
                      <td>0 / 2</td>
                      <td>2</td>
                      <td>-</td>
                    </tr>
                    <tr>
                      <td>그 외</td>
                      <td>2</td>
                      <td></td>
                      <td>2</td>
                    </tr>
                    <tr>
                      <td></td>
                      <td>18 / 17</td>
                      <td></td>
                      <td>일반 선택 : 5</td>
                    </tr>
                  </tbody>
                </table>
              </td>
            </tr>
            <tr>
              <td colSpan="2">일반 선택</td>
              <td>8 / 9</td>
              <td>1</td>
              <td>-</td>
            </tr>
            <tr>
              <td colSpan="2">총합</td>
              <td>139 / 140</td>
              <td></td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Calculator;