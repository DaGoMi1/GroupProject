import React, { useEffect, useState } from 'react';

const Comment = ({ currentPost, changeComment, deleteComment }) => {
  const [newPost, setNewPost] = useState([currentPost]);
  const [editingStates, setEditingStates] = useState([]);
  const [editedComments, setEditedComments] = useState([]);

  useEffect(() => {
    setNewPost([currentPost]);
    // 초기에 모든 댓글에 대해 수정 상태를 false로 설정
    const initialEditingStates = currentPost.comments.map(() => false);
    setEditingStates(initialEditingStates);
  }, [currentPost]);

  const onClickEditBtn = (index) => {
    // 해당 댓글의 수정 상태를 true로 변경
    const updatedEditingStates = [...editingStates];
    updatedEditingStates[index] = true;
    setEditingStates(updatedEditingStates);
  };

  const onClickSaveBtn = (id) => {
    const commentId = newPost[0].comments[id].id;
    changeComment(commentId,editedComments[0]);
    // 해당 댓글의 수정 상태를 false로 변경
    const updatedEditingStates = [...editingStates];
    updatedEditingStates[id] = false;
    setEditingStates(updatedEditingStates);
  };

  const handleCommentChange = (e, index) => {
    const updatedComments = [...editedComments];
    updatedComments[index] = e.target.value;
    setEditedComments(updatedComments);
  };
  return (
    <div>
      {newPost.map((post, postIndex) => (
        post.comments.map((comment, commentIndex) => (
          <div className='commentDetail' key={comment.id}>
            <h4 style={{ margin: '5px' }}>{comment.author}</h4>
            
            {editingStates[commentIndex] ? (
              <input
                type="text"
                value={editedComments[commentIndex]}
                onChange={(e) => handleCommentChange(e, commentIndex)}
              />
            ) : (
              <h4 style={{ margin: '5px' }}>{comment.comment}</h4>
            )}

            {editingStates[commentIndex] ? (
              <button
                className='commentChange'
                onClick={() => onClickSaveBtn(commentIndex)}>
                저장
              </button>
            ) : (
              <button
                onClick={() => onClickEditBtn(commentIndex)}
                className='commentChange'>
                수정
              </button>
            )}
            <button
              onClick={() => {
                deleteComment(comment.id);
              }}
              className='commentDelete'>
              삭제
            </button>
          </div>
        ))
      ))}
    </div>
  );
};

export default Comment;
