package DataView.project.service;

import DataView.project.domain.Member;
import DataView.project.domain.Schedules;
import DataView.project.dto.SchedulesRequest;
import DataView.project.repository.SDJpaMemberRepository;
import DataView.project.repository.SDJpaSchedulesRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class SchedulesService {

    private final SDJpaSchedulesRepository scheduleRepository;
    private final SDJpaMemberRepository memberRepository;


    public SchedulesService(SDJpaSchedulesRepository scheduleRepository, SDJpaMemberRepository memberRepository) {
        this.scheduleRepository = scheduleRepository;
        this.memberRepository = memberRepository;
    }
    public void addSchedule(SchedulesRequest request) {
        //값이 하나라도 없는 경우 error 발생
        if (request.getStartDay() == null || request.getEndDay() == null ||
                request.getTitle() == null || request.getContent() == null ||
                request.getColor() == null ) {
            throw new IllegalArgumentException("실패: 요청 데이터가 부족합니다.");
        }

// 데이터를 받는 부분
        Schedules schedule = new Schedules();
        schedule.setStartDay(request.getStartDay());
        schedule.setEndDay(request.getEndDay());
        schedule.setTitle(request.getTitle());
        schedule.setContent(request.getContent());
        schedule.setColor(request.getColor());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

// 사용자 이름을 기반으로 회원 조회
        Optional<Member> optionalMember = memberRepository.findByUsername(loggedInUsername);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get(); // Optional에서 실제 Member 객체 추출
            // 회원을 찾았으면 회원 아이디를 사용하여 스케줄에 설정
            schedule.setMember(member);
            scheduleRepository.save(schedule);
        } else {
            // 회원을 찾지 못한 경우 예외 처리
            throw new IllegalArgumentException("회원이 존재하지 않습니다. Username: " + loggedInUsername);
        }

    }

    public void updateSchedule(Long userId, Long id, SchedulesRequest request) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            Optional<Schedules> optionalSchedule = scheduleRepository.findById(id);
            if (optionalSchedule.isPresent()) {
                Schedules schedule = optionalSchedule.get();
                if (schedule.getMember().getId().equals(member.getId())) {
                    // 요청에서 받은 내용으로 스케줄을 수정
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
                    throw new IllegalArgumentException("해당 스케줄을 수정할 권한이 없습니다.");
                }
            } else {
                throw new IllegalArgumentException("해당 스케줄을 찾을 수 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
        }
    }



    public void deleteSchedule(Long userId, Long id) {
        // 현재 인증된 사용자의 이름을 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        // 현재 사용자를 조회
        Optional<Member> optionalMember = memberRepository.findByUsername(loggedInUsername);

        if (optionalMember.isPresent()) {
            // 현재 사용자가 존재하면 해당 사용자의 스케줄인지 확인
            Member member = optionalMember.get();
            if (member.getId().equals(userId)) {
                // 현재 사용자와 userId가 일치하는 경우 해당 스케줄을 삭제
                Optional<Schedules> optionalSchedule = scheduleRepository.findById(id);
                if (optionalSchedule.isPresent()) {
                    // 스케줄이 존재하면 삭제 수행
                    Schedules schedule = optionalSchedule.get();
                    if (schedule.getMember().getId().equals(userId)) {
                        // 현재 사용자의 스케줄이면 삭제 수행
                        scheduleRepository.deleteById(id);
                    } else {
                        throw new IllegalArgumentException("해당 스케줄을 삭제할 권한이 없습니다.");
                    }
                } else {
                    throw new IllegalArgumentException("해당 스케줄을 찾을 수 없습니다.");
                }
            } else {
                throw new IllegalArgumentException("해당 사용자의 스케줄을 삭제할 수 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("현재 사용자를 찾을 수 없습니다.");
        }
    }

}


