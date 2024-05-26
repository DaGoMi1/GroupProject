import React from 'react';

const RenderUserDays = ({ daysList }) => {
  const daysElement = daysList.map((day, index) => {
    const className = index === 0 ? 'days sun' : index === 6 ? 'days sat' : 'days';
    return <td className={className} key={index}>{day}</td>;
  });

  return (
    <thead>
      <tr>
        {daysElement}
      </tr>
    </thead>
  )
};

export default RenderUserDays;
