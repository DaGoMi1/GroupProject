package DataView.project.controller;

import DataView.project.domain.*;
import DataView.project.dto.*;
import DataView.project.service.CreditService;
import DataView.project.service.MemberService;
import DataView.project.service.TimeTableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/gpa")
public class GPAController {
    private final CreditService creditService;
    private final TimeTableService timeTableService;
    private final MemberService memberService;

    public GPAController(CreditService creditService,
                         TimeTableService timeTableService,
                         MemberService memberService) {
        this.creditService = creditService;
        this.timeTableService = timeTableService;
        this.memberService = memberService;
    }

    @GetMapping("/credit")
    public ResponseEntity<?> getDataCredit() {
        try {
            DataCredit dataCredit = creditService.getDataCredit(); // 교과구분별 필요 학점 가져오기
            LiberalArtsCredit liberalArtsCredit = creditService.getLiberalCredit(); // 교양선택 소영역별 필요 학점 가져오기

            CreditDTO creditDTO = creditService.getCredit(dataCredit, liberalArtsCredit); // 총 필요 학점 붙여서 반환
            return ResponseEntity.ok(creditDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("불러오기 실패: " + e.getMessage());
        }
    }

    @GetMapping("/member")
    public ResponseEntity<?> getMemberCredit() {
        try {
            Member member = memberService.getMember(); // Member 불러오기

            List<Subject> memberSubject = timeTableService.getAllSubject(member); // Member 수강하는 과목 들고오기

            // 교양선택 소영역 기준으로 학점계산하기
            LiberalArtsCreditDTO liberalArtsCredit = creditService.getMemberLiberalArtsCredit(member, memberSubject);

            // 교과구분으로 학점계산하기
            DataCreditDTO dataCredit = creditService.getMemberDataCredit(memberSubject, liberalArtsCredit);

            GradeDTO gradeDTO = creditService.getGrade(dataCredit, liberalArtsCredit); // 두개 합쳐서 반환
            return ResponseEntity.ok(gradeDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/credit/save")
    public ResponseEntity<?> saveCredit(@RequestBody MemberCreditDTO memberCredit) {
        try {
            Member member = memberService.getMember(); // Member 가져오기

            if (timeTableService.checkMemberSubject(member, memberCredit.getSubjectId())) { // Member가 수강한 과목이라면
                Subject subject = timeTableService.getSubjectById(memberCredit.getSubjectId()); // Subject 가져오기
                creditService.saveCredit(memberCredit.getGrade(), subject); // 학점 저장
                return ResponseEntity.ok().body("학점 저장 완료");
            } else { // Member가 수강한 과목이 아니라면
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("본인이 수강한 과목이 아닙니다");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("학점 저장 실패" + e.getMessage());
        }
    }

    @GetMapping("/credit/member")
    public ResponseEntity<?> memberCreditList() {
        try {
            Member member = memberService.getMember(); // Member 가져오기
            List<Subject> subjects = timeTableService.getAllSubject(member); // Member의 모든 수강 과목 가져오기
            double averageGrade = creditService.getAllCredit(subjects); // 학점 등급 계산
            return ResponseEntity.ok(averageGrade);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
