import React, { useState, useEffect } from 'react';
import Subject from './Subject'; // Subject 컴포넌트를 불러옵니다

const SubjectList = ({ subjectList, onClickAddSubjectBtn }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredSubjectList, setFilteredSubjectList] = useState(subjectList);

  useEffect(() => {
    if (searchTerm === '') {
      setFilteredSubjectList(subjectList);
    } else {
      const startsWithSearchTerm = subjectList.filter(subject =>
        subject.courseName.startsWith(searchTerm)
      );

      setFilteredSubjectList(startsWithSearchTerm);
    }
  }, [searchTerm, subjectList]);
  
  return (
    <div className='subject_area'>
      <input
        type="text"
        placeholder='과목검색'
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      {filteredSubjectList.map(subject => (
        <Subject
          key={subject.id}
          subject={subject}
          onClickAddSubjectBtn={onClickAddSubjectBtn}
        />
      ))}
    </div>
  );
};

export default SubjectList;
