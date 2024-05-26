import React, { useEffect, useState } from 'react';
import AddModal from '../../mainBoard/calendar/AddModal';
import UserChangeModal from './calendar/UserChangeModal';
import UserDeleteModal from './calendar/UserDeleteModal';
import api from '../../../utils/api';
import UserCalendar from './calendar/UserCalendar';
import { format, isWithinInterval, parseISO, addDays, startOfWeek, endOfWeek } from 'date-fns';

const UserCalendarBoard = React.forwardRef((props, ref) => {
  const [isOpenAddModal, setIsOpenAddModal] = useState(false);
  const [isOpenChangeModal, setIsOpenChangeModal] = useState(false);
  const [isOpenDeleteModal, setIsOpenDeleteModal] = useState(false);
  const [schedule, setSchedule] = useState([]);
  const [selectedSchedule, setSelectedSchedule] = useState(null);

  const fetchSchedule = async () => {
    try {
      const response = await api.get('/schedule/member');
      setSchedule(response.data);
    } catch (error) {
      console.log(error.message);      
    }
  };

  useEffect(() => {
    fetchSchedule();
  }, []);

  const onClickCompleteBtn = async (newSchedule) => {
    try {
      const response = await api.post('/schedule', newSchedule);
      if (response.status === 200) {
        const updatedSchedule = await api.get('/schedule/member');
        setSchedule(updatedSchedule.data);
        setIsOpenAddModal(false);
      } else {
        throw new Error("스케줄 추가 실패");
      }
    } catch (error) {
      console.log("데이터 불러오기 실패", error.message);
    }
  };

  const onClickCancelBtn = () => {
    setIsOpenAddModal(false);
  };

  const onClickCompleteChangeBtn = async (updatedSchedule) => {
    try {
      const response = await api.patch('/schedule', updatedSchedule);
      if (response.status === 200) {
        const updatedScheduleData = await api.get('/schedule/member');
        setSchedule(updatedScheduleData.data);
        setIsOpenChangeModal(false);
      } else {
        throw new Error("스케줄 수정 실패");
      }
    } catch (error) {
      console.log("데이터 불러오기 실패", error.message);
    }
  };

  const onClickCancelChangeBtn = () => {
    setIsOpenChangeModal(false);
  };

  const onClickCompleteDeleteBtn = async (scheduleId) => {
    try {
      const response = await api.delete('/schedule', { data: { id: scheduleId } });
      if (response.status === 200) {
        const updatedScheduleData = await api.get('/schedule/member');
        setSchedule(updatedScheduleData.data);
        setIsOpenDeleteModal(false);
      } else {
        throw new Error("스케줄 삭제 실패");
      }
    } catch (error) {
      console.log("데이터 불러오기 실패", error.message);
    }
  };

  const onClickCancelDeleteBtn = () => {
    setIsOpenDeleteModal(false);
  };

  const today = format(new Date(), 'yyyy-MM-dd');
  const todaySchedules = schedule ? schedule.filter(schedule =>
    isWithinInterval(parseISO(today), {
      start: parseISO(schedule.startDay),
      end: parseISO(schedule.endDay)
    })
  ) : [];

  const tomorrow = format(addDays(new Date(), 1), 'yyyy-MM-dd');
  const tomorrowSchedules = schedule ? schedule.filter(schedule =>
    isWithinInterval(parseISO(tomorrow), {
      start: parseISO(schedule.startDay),
      end: parseISO(schedule.endDay)
    })
  ) : [];

  const startOfThisWeek = format(startOfWeek(new Date()), 'yyyy-MM-dd');
  const endOfThisWeek = format(endOfWeek(new Date()), 'yyyy-MM-dd');
  const thisWeekSchedules = schedule ? schedule.filter(schedule =>
    isWithinInterval(parseISO(schedule.startDay), {
      start: parseISO(startOfThisWeek),
      end: parseISO(endOfThisWeek)
    }) ||
    isWithinInterval(parseISO(schedule.endDay), {
      start: parseISO(startOfThisWeek),
      end: parseISO(endOfThisWeek)
    })
  ) : [];

  return (
    <>
      {isOpenAddModal && (
        <AddModal 
          onClickCompleteBtn={onClickCompleteBtn}
          onClickCancelBtn={onClickCancelBtn}
        />
      )}
      {isOpenChangeModal && (
        <UserChangeModal 
          personalSchedule={selectedSchedule}
          onClickCompleteChangeBtn={onClickCompleteChangeBtn}
          onClickCancelChangeBtn={onClickCancelChangeBtn}
        />
      )}
      {isOpenDeleteModal && (
        <UserDeleteModal 
          scheduleId={selectedSchedule?.id}
          onClickCompleteDeleteBtn={onClickCompleteDeleteBtn}
          onClickCancelDeleteBtn={onClickCancelDeleteBtn}
        />
      )}
      <div className="contents">
        <div className='calendarBoard'>
          <div className='box calendar_area'>
            <UserCalendar schedule={schedule} setSelectedSchedule={setSelectedSchedule} />
          </div>
          
            <div className='calendar_btn_area box'>
              <button onClick={() => { setIsOpenAddModal(true) }}>일정 추가하기</button>
              <button onClick={() => { setIsOpenChangeModal(true) }}>일정 변경하기</button>
              <button onClick={() => { setIsOpenDeleteModal(true) }}>일정 삭제하기</button>
            </div>
          </div>
        </div>

    </>
  );
});

export default UserCalendarBoard;
