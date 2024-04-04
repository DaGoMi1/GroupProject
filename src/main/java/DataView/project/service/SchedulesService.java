package DataView.project.service;

import DataView.project.domain.Schedules;
import DataView.project.dto.SchedulesRequest;
import DataView.project.repository.SDJpaSchedulesRepository;


import java.util.Optional;


public class SchedulesService {

    private final SDJpaSchedulesRepository scheduleRepository;


    public SchedulesService(SDJpaSchedulesRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void addSchedule(SchedulesRequest request) {
            Schedules schedule = new Schedules();
            schedule.setStartDay(request.getStartDay());
            schedule.setEndDay(request.getEndDay());
            schedule.setTitle(request.getTitle());
            schedule.setContent(request.getContent());
            schedule.setColor(request.getColor());

            scheduleRepository.save(schedule);
        }

        public void updateSchedule(Long id, SchedulesRequest request) {
            Optional<Schedules> optionalSchedule = scheduleRepository.findById(id);

            if (optionalSchedule.isPresent()) {
                Schedules schedule = optionalSchedule.get();
                // 모든 데이터를 받아서 직접 업데이트
                schedule.setStartDay(request.getStartDay());
                schedule.setEndDay(request.getEndDay());
                schedule.setTitle(request.getTitle());
                schedule.setContent(request.getContent());
                schedule.setColor(request.getColor());

                // 수정된 일정을 저장
                scheduleRepository.save(schedule);
            }
        }

        public void deleteSchedule(Long id) {
            scheduleRepository.deleteById(id);
        }
    }


