package DataView.project.controller;


import DataView.project.dto.SchedulesRequest;
import DataView.project.service.SchedulesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class SchedulesController {
    private  final SchedulesService schedulesService;

    public SchedulesController(SchedulesService schedulesService) {
        this.schedulesService = schedulesService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> schedules(@RequestBody SchedulesRequest request) {
        try {
            schedulesService.addSchedule(request);
            return ResponseEntity.ok().body("일정 추가 완료!");
        } catch (Exception e) {
            // 실패한 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 추가 실패: " + e.getMessage());
        }}

    @PostMapping("/update")
    public ResponseEntity<?> updateSchedule(@RequestParam Long userId, @RequestParam Long id, @RequestBody SchedulesRequest request) {
        try {
            schedulesService.updateSchedule(userId, id, request);
            return ResponseEntity.ok().body("수정 완료!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 실패: " + e.getMessage());
        }
    }


    @PostMapping("/delete")
    public ResponseEntity<?> deleteSchedule(@RequestParam Long userId, @RequestParam Long id) {
        try {
            schedulesService.deleteSchedule(userId, id);
            return ResponseEntity.ok().body("삭제 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패: " + e.getMessage());
        }
    }

}

