package DataView.project.controller;



import DataView.project.dto.SchedulesRequest;
import DataView.project.service.SchedulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class SchedulesController {
    private SchedulesService schedulesService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> schedules(@PathVariable Long id,@RequestBody SchedulesRequest request) {
        schedulesService.updateSchedule(id,request);
        return ResponseEntity.ok().body("일정 추가 완료!");


    }
}

