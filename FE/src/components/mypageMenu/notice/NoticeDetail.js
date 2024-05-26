import React, { useState } from 'react';
import api from '../../../utils/api';


const NoticeDetail = ({ postId, postList, setIsOpenPostModal, user }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState("");
  const [title, setTitle] = useState("");

  const currentPost = postList.filter((post) => post.id === postId)[0];
  
  const onClickEditBtn = () => {
    setIsEditing(true);
    setTitle(currentPost.title);
    setContent(currentPost.content);
  };

  const onClickSaveBtn = async () => {
    try {
      currentPost.title = title;
      currentPost.content = content;
      const response = await api.patch('/posting', {id: currentPost.id, title, content})
      
      if(response.status !== 200){
        throw new Error(response.data);
      }
      
      setIsEditing(false);

      // 저장 후 다시 불러와야할지도모르겠따.
    } catch (error) {
      console.log(error.message);
    }
    

  };

  
  const onClickDeleteBtn = ( async ) => {
    console.log(currentPost, user);
    try {
      const id = currentPost.id;
      const author = currentPost.author
      // 작성자와 동일한지 체크 후 삭제 진행
      if(author !== user){
        throw new Error("작성자 혹은 관리자가 아닙니다.");
      }
      // 혹은 관리자가 맞는지 체크 후 삭제 진행
      const response = api.delete('/posting',{ data: {id} })
      
      if(response.status !==200){
        throw new Error(response.data)
      }
    } catch (error) {
      alert(error.message)
    }
  }

  const onClickCommentBtn = async (event) => {
    event.preventDefault();
    try {
      // 댓글 작성 로직 추가
    } catch (error) {
      // 오류 처리
    }
  };

  return (
    <div className='modal'>
      <div className='modalOverlay'></div>
      <div className="postModal">
        <div style={{ textAlign: "right" }}>
          <button className='exitBtn' onClick={() => { setIsOpenPostModal(false) }}>닫기</button>
        </div>
        
        <div className='postArea'>
          <div className='postTitle'>
            {isEditing ? (
                <textarea 
                  type="text" 
                  className='editTitle' 
                  value={title} 
                  rows={2}
                  cols={50}
                  onChange={(e) => setTitle(e.target.value)} 
                />
              ) : (
                <h2>{currentPost.title}</h2>
              )}
            <div className="contentBox">
              {isEditing ? (
                <textarea 
                  className= 'editBox'
                  rows={10}
                  cols={50}
                  value={content} 
                  onChange={(e) => setContent(e.target.value)}
                />
              ) : (
                currentPost.content
              )}
            </div>
            {isEditing ? (
              <button className='completeBtn' onClick={onClickSaveBtn}>저장하기</button>
            ) : (
              <button className='completeBtn' onClick={onClickEditBtn}>수정하기</button>
            )}
            <button className='cancelBtn' onClick={onClickDeleteBtn}>삭제하기</button>
          </div>

          <div>
            <h2>댓글</h2>
            <div className="comment">
              {/* 댓글 내용을 여기에 추가 */}
            </div>

            <form onSubmit={onClickCommentBtn}>
              <input type="text" className='inputComment' />
              <button
                onClick={onClickCommentBtn}
                className='completeCommentBtn'>작성</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default NoticeDetail;
