import React, { useEffect, useState } from "react";
import api from "../../../utils/api";
import Free from "./Free";
import FreeDetail from "./FreeDetail";
import MakePost from "../notice/MakePost";

const FreeArea = ({ user }) => {
  const [postList, setPostList] = useState([]);
  const [isOpenPostModal, setIsOpenPostModal] = useState(false);
  const [postId, setPostId] = useState(null);
  const [isMakePostOpenModal, setIsMakePostOpenModal] = useState(false);

  const getPostList = async () => {
    try {
      const response = await api.post('/posting/list', { boardType: 'free' });
      if (response.status !== 200) {
        throw new Error(response.data);
      }
      setPostList(response.data);
      
    } catch (error) {
      console.log(error.message);
    }
  };

  const onClickPostBtn = (id) => {
    setIsOpenPostModal(true);
    setPostId(id);
  };

  useEffect(() => {
    getPostList();
  }, []);

  return (
    <div className="noticePageWrap">
      {isOpenPostModal && (
        <FreeDetail 
          postId={postId} 
          postList={postList}
          setIsOpenPostModal={setIsOpenPostModal}
          user={user}
          getPostList={getPostList}
        />
      )}

      {isMakePostOpenModal && (
        <MakePost
          getPostList={getPostList}
          setPostList={setPostList}
          setIsMakePostOpenModal={setIsMakePostOpenModal}
        />
      )}
      
      <table className="noticeTable">
        <thead>
          <tr>
            <td className="noticeTdIndex ftIndex">구분</td>
            <td className="noticeTdTitle ftTitle">제목</td>
            <td className="noticeTdWriter ftWriter">작성자</td>
            <td className="noticeTdDate ftDate">날짜</td>
          </tr>
        </thead>
        <tbody>
          {postList.map((post, index) => (
            <Free
              key={post.id}
              id={post.id}
              title={post.title}
              content={post.content}
              boardType={post.boardType}
              createdAt={post.createdAt}
              author={post.author}
              onClickPostBtn={onClickPostBtn}
            />
          ))}
        </tbody>

        <button 
          className="makePostBtn"
          onClick={() => setIsMakePostOpenModal(true)}>글쓰기
        </button>
      </table>
    </div>
  );
};

export default FreeArea;
