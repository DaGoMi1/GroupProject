package DataView.project.service;

import DataView.project.domain.Member;
import DataView.project.domain.Schedules;
import DataView.project.repository.SDJpaMemberRepository;
import DataView.project.repository.SDJpaPostingRepository;
import DataView.project.repository.SDJpaSchedulesRepository;

public class AdminService {
    private final SDJpaPostingRepository postingRepository;
    private final SDJpaSchedulesRepository schedulesRepository;
    private final SDJpaMemberRepository memberRepository;

    public AdminService(SDJpaPostingRepository postingRepository,
                        SDJpaSchedulesRepository schedulesRepository,
                        SDJpaMemberRepository memberRepository) {
        this.postingRepository = postingRepository;
        this.schedulesRepository = schedulesRepository;
        this.memberRepository = memberRepository;
    }

    public void grantAdminRole(Member member) {
        member.setRole("ROLE_ADMIN");
        memberRepository.save(member);
    }

    public void revokeAdminRole(Member member) {
        member.setRole("ROLE_USER");
        memberRepository.save(member);
    }

    public void deletePosting(Long postingId) throws Exception {
        if (postingRepository.existsById(postingId)) {
            postingRepository.deleteById(postingId);
        } else {
            throw new Exception("존재하지 않는 게시글 아이디입니다.");
        }
    }

    public void deleteComment(Long commentId) throws Exception {
        if (postingRepository.existsById(commentId)) {
            postingRepository.deleteById(commentId);
        } else {
            throw new Exception("존재하지 않는 댓글 아이디입니다.");
        }
    }

    public void deleteSchedule(Long scheduleId) throws Exception {
        if (schedulesRepository.existsById(scheduleId)) {
            schedulesRepository.deleteById(scheduleId);
        } else {
            throw new Exception("존재하지 않는 스케줄 아이디입니다.");
        }
    }

    public void addSchedule(Schedules schedules) throws Exception {
        if (schedules.getId() == null && schedules.getMember() == null) {
            schedulesRepository.save(schedules);
        } else {
            throw new Exception("스케줄 아이디와 스케줄과 관련된 Member가 비어있어야 합니다.");
        }
    }

    public void updateSchedule(Schedules schedules) throws Exception {
        if (schedules.getId() != null && schedules.getMember() == null) {
            schedulesRepository.findById(schedules.getId()).ifPresent(schedule -> {
                schedule.setTitle(schedules.getTitle());
                schedule.setColor(schedules.getColor());
                schedule.setStartDay(schedules.getStartDay());
                schedule.setEndDay(schedules.getEndDay());
                schedulesRepository.save(schedule);
            });
        } else {
            throw new Exception("스케줄 아이디가 없거나, Member가 비어있지 않습니다.");
        }
    }
}
