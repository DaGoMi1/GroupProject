import React,{useEffect, useState} from "react";
import api from "../../../utils/api";
import Notice from "./Notice";
import NoticeDetail from "./NoticeDetail";

const NoticeArea = ({user}) => {
  const [postList, setPostList] = useState([]);
  const [isOpenPostModal, setIsOpenPostModal] = useState(false);
  const [postId, setPostId] = useState(null);
  const getPostList = async() => {
    try {
      // const response = api.post('/posting/list', 'notice');
      // if(response.status !== 200){
      //   throw new Error(response.data);
      // }
      // setPostList(response.data);
      
      // 테스트 코드
      setPostList([
        {
          id : 1,
          title : '축구할사람',
          content : '6/1 일 학교 운동장에서 축구하실분 모집합니다.',
          boardType : 'notice',
          time : '2024-05-26',
          author : '정상인'
        },
        {
          id : 2,
          title : '농구할사람',
          content : '6/1 일 학교 운동장에서 농구하실분 모집합니다.',
          boardType : 'notice',
          time : '2024-05-27',
          author : '이다검'
        },
      ])
    } catch (error) {
      console.log(error.message);
    }
  }

  const onClickPostBtn = (id) => {
    setIsOpenPostModal(true);
    setPostId(id);
  }

  useEffect(()=>{
    getPostList();
  },[])
  return (
      <div className="noticePageWrap">
        {isOpenPostModal 
          ? <NoticeDetail 
              postId={postId} 
              postList={postList}
              setIsOpenPostModal ={setIsOpenPostModal} 
              user = {user}
            /> 
          : null}
        <table className="noticeTable">
          <thead>
            <tr>
              <td className="noticeTdIndex ntIndex">구분</td>
              <td className="noticeTdTitle ntTitle">제목</td>
              <td className="noticeTdWriter ntWriter">작성자</td>
              <td className="noticeTdDate ntDate">날짜</td>
            </tr>
          </thead>
          <tbody>
            {postList.map((post,index)=>{
              return <Notice
                id = {post.id}
                title = {post.title}
                content = {post.content}
                boardType = {post.boardType}
                time = {post.time}
                author = {post.author}
                onClickPostBtn = {onClickPostBtn}
              />
            })}
          </tbody>
        </table>
      </div>
  );
};

export default NoticeArea;