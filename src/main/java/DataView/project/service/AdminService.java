package DataView.project.service;

import DataView.project.domain.Schedules;
import DataView.project.repository.SDJpaPostingRepository;
import DataView.project.repository.SDJpaSchedulesRepository;

public class AdminService {
    private final SDJpaPostingRepository postingRepository;
    private final SDJpaSchedulesRepository schedulesRepository;

    public AdminService(SDJpaPostingRepository postingRepository, SDJpaSchedulesRepository schedulesRepository) {
        this.postingRepository = postingRepository;
        this.schedulesRepository = schedulesRepository;
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
        if (postingRepository.existsById(scheduleId)) {
            postingRepository.deleteById(scheduleId);
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
}
