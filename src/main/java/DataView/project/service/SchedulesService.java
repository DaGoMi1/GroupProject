package DataView.project.service;

import DataView.project.domain.Member;
import DataView.project.domain.Schedules;
import DataView.project.dto.ScheduleDTO;
import DataView.project.dto.SchedulesRequest;
import DataView.project.repository.SDJpaMemberRepository;
import DataView.project.repository.SDJpaSchedulesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SchedulesService {

    private final SDJpaSchedulesRepository scheduleRepository;
    private final SDJpaMemberRepository memberRepository;

    public SchedulesService(SDJpaSchedulesRepository scheduleRepository, SDJpaMemberRepository memberRepository) {
        this.scheduleRepository = scheduleRepository;
        this.memberRepository = memberRepository;
    }

    public void addSchedule(SchedulesRequest request, Member member) {
        Schedules schedule = new Schedules();
        schedule.setStartDay(request.getStartDay());
        schedule.setEndDay(request.getEndDay());
        schedule.setTitle(request.getTitle());
        schedule.setContent(request.getContent());
        schedule.setColor(request.getColor());
        schedule.setMember(member);

        scheduleRepository.save(schedule);
    }

    public void updateSchedule(SchedulesRequest request, Member member) throws Exception {
        Optional<Schedules> optionalSchedule = scheduleRepository.findById(request.getScheduleId());

        if (optionalSchedule.isPresent()) {
            Schedules schedule = optionalSchedule.get();

            if (!schedule.getMember().getId().equals(member.getId())) {
                throw new Exception("사용자의 스케줄이 아닙니다.");
            }

            if (request.getStartDay() != null) {
                schedule.setStartDay(request.getStartDay());
            }
            if (request.getEndDay() != null) {
                schedule.setEndDay(request.getEndDay());
            }
            if (request.getTitle() != null) {
                schedule.setTitle(request.getTitle());
            }
            if (request.getContent() != null) {
                schedule.setContent(request.getContent());
            }
            if (request.getColor() != null) {
                schedule.setColor(request.getColor());
            }

            scheduleRepository.save(schedule);
        } else {
            throw new Exception("존재하지 않는 스케줄 아이디입니다.");
        }
    }

    public void deleteSchedule(SchedulesRequest request, Member member) throws Exception {
        if (member.getId().equals(request.getScheduleId())) {
            scheduleRepository.deleteById(request.getScheduleId());
        } else {
            throw new Exception("사용자의 스케줄이 아닙니다.");
        }
    }

    public List<ScheduleDTO> getMemberList(Member member) {
        member = memberRepository.findByIdWithSchedules(member.getId());
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

        for (Schedules schedule : member.getSchedules()) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setStartDay(schedule.getStartDay());
            scheduleDTO.setEndDay(schedule.getEndDay());
            scheduleDTO.setTitle(schedule.getTitle());
            scheduleDTO.setContent(schedule.getContent());
            scheduleDTO.setColor(schedule.getColor());

            scheduleDTOs.add(scheduleDTO);
        }

        return scheduleDTOs;
    }

    public List<Schedules> getDataList() {
        List<Schedules> schedules = new ArrayList<>();
        List<Schedules> allSchedules = scheduleRepository.findAll();

        for (Schedules schedule : allSchedules) {
            if (schedule.getMember() == null) {
                schedules.add(schedule);
            }
        }

        return schedules;
    }

}


