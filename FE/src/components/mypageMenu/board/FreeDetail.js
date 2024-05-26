import React, { useState } from 'react';
import api from '../../../utils/api';
import Comment from '../notice/Comment';

const FreeDetail = ({ getPostList, postId, postList, setIsOpenPostModal, user }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState("");
  const [title, setTitle] = useState("");
  const [comment, setComment] = useState('');
  const currentPost = postList.find((post) => post.id === postId);

  const onClickEditBtn = () => {
    setIsEditing(true);
    setTitle(currentPost.title);
    setContent(currentPost.content);
  };

  const onClickSaveBtn = async () => {
    try {
      currentPost.title = title;
      currentPost.content = content;
      const response = await api.patch('/posting', { id: currentPost.id, title, content });
      
      if (response.status !== 200) {
        throw new Error(response.data);
      }
      
      setIsEditing(false);
      getPostList();
    } catch (error) {
      console.log(error.message);
    }
  };

  const onClickDeleteBtn = async () => {
    try {
      const id = currentPost.id;
      const response = await api.delete('/posting', { data: { id } });
      if (response.status === 200) {
        getPostList();
        setIsOpenPostModal(false);
      }
    } catch (error) {
      console.log(error.message);
    }
  };

  const onClickCommentBtn = async (event) => {
    event.preventDefault();
    try {
      const response = await api.post('/posting/comment', { posting: currentPost, comment });
      if (response.status === 200) {
        getPostList();
      }
    } catch (error) {
      console.log(error.message);
    }
  };

  const changeComment = async (id, comment) => {
    try {
      const response = await api.patch('/posting/comment', { id, comment });
      if (response.status === 200) {
        getPostList();
      }
    } catch (error) {
      console.log(error.message);
    }
  };

  const deleteComment = async (id) => {
    try {
      const response = await api.delete('/posting/comment', { data: { id } });
      if (response.status === 200) {
        getPostList();
      }
    } catch (error) {
      console.log(error.message);
    }
  };

  return (
    <div className='modal'>
      <div className='modalOverlay'></div>
      <div className="postModal">
        <div style={{ textAlign: "right" }}>
          <button className='exitBtn' onClick={() => setIsOpenPostModal(false)}>닫기</button>
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
                  className='editBox'
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
              <Comment 
                changeComment={changeComment}
                deleteComment={deleteComment}
                currentPost={currentPost}
              />
            </div>

            <form onSubmit={onClickCommentBtn}>
              <textarea 
                value={comment}
                onChange={(e) => setComment(e.target.value)}
                type="text" className='inputComment' 
              />
              <button
                onClick={onClickCommentBtn}
                className='completeCommentBtn'>작성
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FreeDetail;
