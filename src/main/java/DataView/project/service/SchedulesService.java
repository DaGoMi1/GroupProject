package DataView.project.service;


import DataView.project.domain.Schedules;
import DataView.project.dto.SchedulesRequest;
import DataView.project.repository.SDJpaSchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchedulesService {
    @Autowired
    private SDJpaSchedulesRepository sdJpaSchedulesRepository;

    public void updateSchedule(Long id, SchedulesRequest request) {
        // 스케줄 식별을 위해 ID를 사용하여 해당 스케줄을 찾음
        Optional<Schedules> optionalSchedule = sdJpaSchedulesRepository.findById(id);

        if (optionalSchedule.isPresent()) {
            Schedules schedule = optionalSchedule.get();

            // 요청에 따라 스케줄 필드를 업데이트
            if (request.getTitle() != null) {
                schedule.setTitle(request.getTitle());
            }
            if (request.getContent() != null) {
                schedule.setContent(request.getContent());
            }
            if (request.getStartDay() != null) {
                schedule.setStartDay(request.getStartDay());
            }
            if (request.getEndDay() != null) {
                schedule.setEndDay(request.getEndDay());
            }
            if (request.getColor() != null) {
                schedule.setColor(request.getColor());
            }

            // 업데이트된 스케줄을 저장
            sdJpaSchedulesRepository.save(schedule);
        }
        // 스케줄이 존재하지 않는 경우 예외 처리


    }}
