package DataView.project.controller;


import DataView.project.dto.SchedulesRequest;
import DataView.project.service.SchedulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class SchedulesController {
    private  final SchedulesService schedulesService;

    public SchedulesController(SchedulesService schedulesService) {
        this.schedulesService = schedulesService;
    }


    @PatchMapping("/add/{id}")
    public ResponseEntity<?> schedules(@RequestBody SchedulesRequest request) {
        schedulesService.addSchedule(request);
        return ResponseEntity.ok().body("일정 추가 완료!");
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @RequestBody SchedulesRequest request) {
        // 서비스를 호출하여 스케줄 업데이트 작업을 수행
        schedulesService.updateSchedule(id,request);
        return ResponseEntity.ok("수정 성공!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        schedulesService.deleteSchedule(id);
        return ResponseEntity.ok().body("삭제 성공!");
    }
}

