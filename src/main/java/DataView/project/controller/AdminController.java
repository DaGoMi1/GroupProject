package DataView.project.controller;

import DataView.project.dto.SchedulesRequest;
import DataView.project.service.SchedulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private  final SchedulesService schedulesService;

    public AdminController(SchedulesService schedulesService) {
        this.schedulesService = schedulesService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdminSchedule(@RequestBody SchedulesRequest request) {
        schedulesService.addSchedule(request);
        return ResponseEntity.ok().body("관리자의 일정 추가 완료!");
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateAdimSchedule(@PathVariable Long id, @RequestBody SchedulesRequest request) {
        // 서비스를 호출하여 스케줄 업데이트 작업을 수행
        schedulesService.updateSchedule(id,request);
        return ResponseEntity.ok("관리자의 일정 수정 성공!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdminSchedule(@PathVariable Long id) {
        schedulesService.deleteSchedule(id);
        return ResponseEntity.ok().body("관리자의 일정 삭제 성공!");
    }

}
