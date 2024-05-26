package DataView.project.controller;


import DataView.project.domain.Member;
import DataView.project.domain.Schedules;
import DataView.project.dto.ScheduleDTO;
import DataView.project.dto.SchedulesRequest;
import DataView.project.service.MemberService;
import DataView.project.service.SchedulesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class SchedulesController {
    private final SchedulesService schedulesService;
    private final MemberService memberService;

    public SchedulesController(SchedulesService schedulesService, MemberService memberService) {
        this.schedulesService = schedulesService;
        this.memberService = memberService;
    }


    @PostMapping("")
    public ResponseEntity<?> schedules(@RequestBody SchedulesRequest request) {
        try {
            Member member = memberService.getMember();
            schedulesService.addSchedule(request, member);
            return ResponseEntity.ok().body("일정 추가 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 추가 실패: " + e.getMessage());
        }
    }

    @PatchMapping("")
    public ResponseEntity<?> updateSchedule(@RequestBody SchedulesRequest request) {
        try {
            Member member = memberService.getMember();
            schedulesService.updateSchedule(request, member);
            return ResponseEntity.ok().body("수정 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정 실패: " + e.getMessage());
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteSchedule(@RequestBody SchedulesRequest request) {
        try {
            Member member = memberService.getMember();
            schedulesService.deleteSchedule(request, member);
            return ResponseEntity.ok().body("삭제 성공!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("삭제 실패: " + e.getMessage());
        }
    }

    @GetMapping("/member")
    public ResponseEntity<?> listMember() {
        try {
            Member member = memberService.getMember();
            List<ScheduleDTO> schedulesList = schedulesService.getMemberList(member);
            return ResponseEntity.ok(schedulesList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> listData() {
        try {
            List<Schedules> schedulesList = new ArrayList<>();
            if (schedulesService.getDataList() != null) {
                schedulesList = schedulesService.getDataList();
            }
            return ResponseEntity.ok(schedulesList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

