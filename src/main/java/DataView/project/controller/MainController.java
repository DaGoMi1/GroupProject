package DataView.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class MainController {
    @GetMapping("/test")
    public ResponseEntity<?> testing() {
        return ResponseEntity.ok().body("세션 유지 중");
    }
}
