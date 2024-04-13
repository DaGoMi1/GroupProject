package DataView.project.controller;

import DataView.project.domain.DataCredit;
import DataView.project.domain.LiberalArtsCredit;
import DataView.project.domain.Subject;
import DataView.project.dto.*;
import DataView.project.service.CreditService;
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

    public GPAController(CreditService creditService,
                         TimeTableService timeTableService) {
        this.creditService = creditService;
        this.timeTableService = timeTableService;
    }

    @GetMapping("/credit")
    public ResponseEntity<?> getDataCredit() {
        try {
            DataCredit dataCredit = creditService.getDataCredit();
            LiberalArtsCredit liberalArtsCredit = creditService.getLiberalCredit();

            CreditDTO creditDTO = creditService.getCredit(dataCredit, liberalArtsCredit);
            return ResponseEntity.ok(creditDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("불러오기 실패" + e.getMessage());
        }
    }

    @GetMapping("/member")
    public ResponseEntity<?> getMemberCredit() {
        try {
            List<Subject> memberSubject = timeTableService.getAllSubject();
            LiberalArtsCreditDTO liberalArtsCredit = creditService.getMemberLiberalArtsCredit(memberSubject);
            DataCreditDTO dataCredit = creditService.getMemberDataCredit(memberSubject, liberalArtsCredit);

            GradeDTO gradeDTO = new GradeDTO();
            gradeDTO.setDataCreditDTO(dataCredit);
            gradeDTO.setLiberalArtsCreditDTO(liberalArtsCredit);
            return ResponseEntity.ok(gradeDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("불러오기 실패" + e.getMessage());
        }
    }
}
