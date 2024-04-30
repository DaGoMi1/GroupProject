package DataView.project.controller;

import DataView.project.domain.Comment;
import DataView.project.domain.Posting;
import DataView.project.domain.Schedules;
import DataView.project.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/posting/delete")
    public ResponseEntity<?> postingDelete(@RequestBody Posting posting) {
        try {
            adminService.deletePosting(posting.getId());
            return ResponseEntity.ok().body("게시글 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/comment/delete")
    public ResponseEntity<?> commentDelete(@RequestBody Comment comment) {
        try {
            adminService.deleteComment(comment.getId());
            return ResponseEntity.ok().body("댓글 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/schedule/delete")
    public ResponseEntity<?> scheduleDelete(@RequestBody Schedules schedules) {
        try {
            adminService.deleteSchedule(schedules.getId());
            return ResponseEntity.ok().body("스케줄 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/schedule/add")
    public ResponseEntity<?> scheduleAdd(@RequestBody Schedules schedules) {
        try {
            adminService.addSchedule(schedules); // 학과 스케줄 저장
            return ResponseEntity.ok().body("스케줄 저장 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
