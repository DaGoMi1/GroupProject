package DataView.project.controller;

import DataView.project.domain.Member;
import DataView.project.domain.Subject;
import DataView.project.domain.TimeTable;
import DataView.project.dto.CourseDTO;
import DataView.project.dto.SubjectDTO;
import DataView.project.dto.SubjectRequest;
import DataView.project.service.MemberService;
import DataView.project.service.TimeTableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-table")
public class TimeTableController {
    private final TimeTableService timeTableService;
    private final MemberService memberService;

    public TimeTableController(TimeTableService timeTableService,
                               MemberService memberService) {
        this.timeTableService = timeTableService;
        this.memberService = memberService;
    }

    @PostMapping("/subject/save")
    public ResponseEntity<?> saveSubject(@RequestBody SubjectRequest request) {
        try {
            Member member = memberService.getMember(); // 현재 사용자 가져오기
            int grade = request.getGrade(); // 변수에 grade 저장
            String semester = request.getSemester(); // 변수에 semester 저장

            // 사용자의 grade학년 semester학기 TimeTable 객체 가져오기
            TimeTable timeTable = timeTableService.loadTimeTable(member, grade, semester);

            // 수강하고 싶은 과목 ID ,Subject 객체로 받아오기
            Subject subject = timeTableService.getSubjectById(request.getSubjectId());

            // 받아온 TimeTable 객체에 연결해서 수강하고 싶은 Subject 저장
            timeTableService.addSubject(timeTable, subject);

            return ResponseEntity.ok().body("저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장 실패 " + e.getMessage());
        }
    }

    @DeleteMapping("/subject/delete")
    public ResponseEntity<?> deleteSubject(@RequestBody SubjectRequest request) {
        try {
            Member member = memberService.getMember(); // 현재 사용자 가져오기

            // Subject 객체가 현재 사용자 Member의 것이라면
            if (timeTableService.checkMemberSubject(member, request.getSubjectId())) {
                timeTableService.deleteMemberSubject(request.getSubjectId());
                return ResponseEntity.ok("삭제완료");
            } else {
                // Member의 것이 아니라면
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("본인이 수강한 과목이 아닙니다");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패 " + e.getMessage());
        }
    }

    @GetMapping("/member/list")
    public ResponseEntity<?> getMemberList(@RequestParam int grade, @RequestParam String semester) {
        try {
            Member member = memberService.getMember(); // 현재 사용자 가져오기

            // Member의 grade학년 semester학기의 수강한 과목들 리스트로 가져오기
            List<SubjectDTO> subjectList = timeTableService.getMemberSubjectList(member, grade, semester);
            return ResponseEntity.ok(subjectList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("불러오기 실패: " + e.getMessage());
        }
    }

    @GetMapping("/subject/list")
    public ResponseEntity<?> getSubjectList(@RequestParam int year,
                                            @RequestParam String semester,
                                            @RequestParam String curriculumType) {
        try {
            // year, semester, curriculumType에 맞는 Course(수강 가능 과목들의 모임) 가져오기
            CourseDTO course = timeTableService.getCourse(year, semester, curriculumType);

            // Course에 맞는 수강 가능 과목들 리스트 가져오기
            List<SubjectDTO> subjects = timeTableService.getSubjectList(course);
            return ResponseEntity.ok(subjects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("불러오기 실패");
        }
    }
}
