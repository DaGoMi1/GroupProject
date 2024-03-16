package DataView.project.controller;

import DataView.project.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final EmailService emailService;

    public HomeController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("")
    public String home(){
        return "home";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/send-email")
    @ResponseBody
    public String sendEmail(@RequestParam String email) {
        try {
            // 이메일 보내기
            int verificationCode = emailService.send(email);

            // 성공적으로 이메일을 보냈을 때, 생성된 인증번호를 리턴
            return String.valueOf(verificationCode);
        } catch (Exception e) {
            return "Error: " + e.getMessage(); // 실패 시 에러 메시지를 포함한 문자열 리턴
        }
    }





}
