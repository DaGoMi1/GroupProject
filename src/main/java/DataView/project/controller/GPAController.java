package DataView.project.controller;

import DataView.project.domain.DataCredit;
import DataView.project.domain.LiberalArtsCredit;
import DataView.project.domain.Member;
import DataView.project.domain.Subject;
import DataView.project.dto.*;
import DataView.project.service.CreditService;
import DataView.project.service.MemberService;
import DataView.project.service.TimeTableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("불러오기 실패" + e.getMessage());
        }
    }

    @GetMapping("/member")
    public ResponseEntity<?> getMemberCredit() {
        try {
            Member member = memberService.getMember(); // Member 불러오기

            List<Subject> memberSubject = timeTableService.getAllSubject(member); // Member 수강하 과목 들고오기

            // 교양선택 소영역 기준으로 학점계산하기
            LiberalArtsCreditDTO liberalArtsCredit = creditService.getMemberLiberalArtsCredit(member, memberSubject);

            // 교과구분으로 학점계산하기
            DataCreditDTO dataCredit = creditService.getMemberDataCredit(memberSubject, liberalArtsCredit);

            GradeDTO gradeDTO = creditService.getGrade(dataCredit, liberalArtsCredit); // 두개 합쳐서 반환
            return ResponseEntity.ok(gradeDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("불러오기 실패" + e.getMessage());
        }
    }
}
