import React,{useEffect, useState} from "react";
import api from "../../../utils/api";
import Notice from "./Notice";
import NoticeDetail from "./NoticeDetail";
import MakePost from "./MakePost";

const NoticeArea = ({user}) => {
  const [postList, setPostList] = useState([]);
  const [isOpenPostModal, setIsOpenPostModal] = useState(false);
  const [postId, setPostId] = useState(null);
  const [isMakePostOpenModal, setIsMakePostOpenModal] = useState(false);
  const getPostList = async() => {
    try {
      const response = await api.post('/posting/list', {boardType : 'notice'});
      if(response.status !== 200){
        throw new Error(response.data);
      }
      setPostList(response.data);
      
     
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
              getPostList={getPostList}
            /> 
          : null}

        {
          isMakePostOpenModal
            ? <MakePost
                getPostList={getPostList}
                setPostList = {setPostList}
                setIsMakePostOpenModal={setIsMakePostOpenModal}
              />
            : null
        }
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
                createdAt = {post.createdAt}
                author = {post.author}
                onClickPostBtn = {onClickPostBtn}
              />
            })}
          </tbody>

          <button 
            className="makePostBtn"
            onClick={()=>{setIsMakePostOpenModal(true)}}>글쓰기
            </button>
        </table>
      </div>
  );
};

export default NoticeArea;