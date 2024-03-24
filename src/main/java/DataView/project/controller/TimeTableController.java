package DataView.project.controller;

import DataView.project.domain.Member;
import DataView.project.domain.Subject;
import DataView.project.domain.TimeTable;
import DataView.project.dto.CustomUserDetails;
import DataView.project.dto.SubjectRequest;
import DataView.project.service.TimeTableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time-table")
public class TimeTableController {
    private final TimeTableService timeTableService;

    public TimeTableController(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    @PostMapping("/subject/save")
    public ResponseEntity<?> saveSubject(@RequestBody SubjectRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            Member member = userDetails.member();
            int grade = request.getGrade();
            String semester = request.getSemester();

            TimeTable timeTable = timeTableService.loadTimeTable(member, grade, semester);
            Subject subject = timeTableService.mapSubjectRequestToSubject(request);

            timeTableService.addSubject(timeTable, subject);

            return ResponseEntity.ok().body("저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장 실패");
        }
    }
}
