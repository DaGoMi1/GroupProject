import React from 'react';

const Free = ({ id, title, content, boardType, createdAt, author, onClickPostBtn }) => {
  return (
    <tr>
      <td className="noticeTdNumber">{id}</td>
      <td className="noticeTdTitle notCenter">
        <button
          className='onClickPostBtn'
          onClick={() => onClickPostBtn(id)}
        >{title}</button>
      </td>
      <td className="noticeTdWriter">{author}</td>
      <td className="noticeTdDate">{createdAt}</td>
    </tr>
  );
};

export default Free;
