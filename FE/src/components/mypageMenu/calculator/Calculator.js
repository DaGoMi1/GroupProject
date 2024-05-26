import React, { useState, useEffect } from "react";
import api from "../../../utils/api";
const Calculator = () => {
  const [userCredit, setUserCredit] = useState({
    dataCreditDTO:{
      "majorBasic" : 2,
      "majorElective" : 12,
      "majorCompulsory": 0,
      "generalElective": 0,
      "liberalArtsCompulsory":0,
      "liberalArtsElective":2,
      "total": 14,
    },
    liberalArtsCreditDTO: {
      "globalCommunication" : 2,
      "maritimeCulture" : 0,
      "maritimeSportsArts" : 0,
      "historyCulture" : 0,
      "societyEconomy" : 0,
      "personalDevelopment" : 2,
      "total" : 2,
      "rest" : 0,
    }
  });
  const [averageScore, setAverageScore] = useState(0);
  const [errorMessage, setErrorMessage] = useState(null);
  const getGpa = async () => {
    try {
      const response = await api.get('/gpa/member');
      if(response.status === 200) {
        setUserCredit(response.data);
      } else {
        throw new Error(response.data);
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  }

  const getAverageScore = async () => {
    try {
      const response = await api.get('/gpa/credit/member');
      if(response.status === 200) {
        setAverageScore(response.data);
      } else {
        throw new Error(response.data)
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  }

  const calculateRestAndOver = (current,max) => {
    const rest =
     ( max - current <= 0) ? 0 : (max - current );
    const over = 
      ( current - max ) <= 0 ? 0 : (current - max);
    
    return [rest,over]
  }

  useEffect(()=>{
    getGpa();
    getAverageScore();
  },[])
  return (
    <div>
      <div className="calculatorWrap">
        {errorMessage 
          ? <div className="errorMessageWrap">{errorMessage}</div> 
          : <h3>평균학점 : {averageScore} / 4.5</h3>}
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
              <td>{userCredit?.dataCreditDTO.majorBasic} / 20</td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.majorBasic,20)[0]}
              </td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.majorBasic,20)[1]}
              </td>
            </tr>
            <tr>
              <td>필수</td>
              <td>{userCredit?.dataCreditDTO.majorCompulsory} / 46</td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.majorCompulsory,46)[0]}
              </td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.majorCompulsory,46)[1]}
              </td>
            </tr>
            <tr>
              <td>선택</td>
              <td>{userCredit?.dataCreditDTO.majorElective} / 24</td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.majorElective,24)[0]}
              </td>
              <td>3</td>
            </tr>
            <tr>
              <td rowSpan="2">교양</td>
              <td>필수</td>
              <td>{userCredit?.dataCreditDTO.liberalArtsCompulsory} / 24</td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.liberalArtsCompulsory,24)[0]}
              </td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.liberalArtsCompulsory,24)[1]}
              </td>
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
                      <td>{userCredit?.liberalArtsCreditDTO.globalCommunication} / 2</td>
                      <td>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.globalCommunication,2)[0]}
                      </td>
                      <td>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.globalCommunication,2)[1]}
                      </td>
                    </tr>
                    <tr>
                      <td>해양문화</td>
                      <td>{userCredit?.liberalArtsCreditDTO.maritimeCulture} / 2</td>
                      <td>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.maritimeCulture,2)[0]}
                      </td>
                      <td>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.maritimeCulture,2)[1]}
                      </td>
                    </tr>
                    <tr>
                      <td>해양스포츠와예술</td>
                      <td>{userCredit?.liberalArtsCreditDTO.maritimeSportsArts} / 2</td>
                      <td>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.maritimeSportsArts,2)[0]}
                      </td>
                      <td>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.maritimeSportsArts,2)[1]}
                      </td>
                    </tr>
                    <tr>
                      <td>역사와문화</td>
                      <td>{userCredit?.liberalArtsCreditDTO.historyCulture} / (4/5)</td>
                      <td rowSpan={2}>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.historyCulture 
                          + userCredit?.liberalArtsCreditDTO.societyEconomy , 6)[0]}
                      </td>
                      <td rowSpan={2}>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.historyCulture 
                          + userCredit?.liberalArtsCreditDTO.societyEconomy , 6)[1]}
                      </td>
                    </tr>
                    <tr>
                      <td>사회와경제</td>
                      <td>{userCredit?.liberalArtsCreditDTO.societyEconomy} / (4/5)</td>
                    </tr>
                    <tr>
                      <td>인성</td>
                      <td>{userCredit?.liberalArtsCreditDTO.personalDevelopment} / 2</td>
                      <td>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.personalDevelopment,2)[0]}
                      </td>
                      <td>
                        {calculateRestAndOver(userCredit?.liberalArtsCreditDTO.personalDevelopment,2)[1]}
                      </td>
                    </tr>
                    <tr>
                      <td>그 외</td>
                      <td>{userCredit?.liberalArtsCreditDTO.rest}</td>
                      <td></td>
                      <td>{userCredit?.liberalArtsCreditDTO.rest}</td>
                    </tr>
                    <tr>
                      <td></td>
                      <td>{userCredit?.dataCreditDTO.liberalArtsElective} / 17</td>
                      <td></td>
                      <td></td>
                    </tr>
                  </tbody>
                </table>
              </td>
            </tr>
            <tr>
              <td colSpan="2">일반 선택</td>
              <td>{userCredit?.dataCreditDTO.generalElective} / 9</td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.generalElective,9)[0]}
                </td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.generalElective,9)[1]}
              </td>
            </tr>
            <tr>
              <td colSpan="2">총합</td>
              <td>{userCredit?.dataCreditDTO.total} / 140</td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.total,140)[0]}
              </td>
              <td>
                {calculateRestAndOver(userCredit?.dataCreditDTO.total,140)[1]}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Calculator;