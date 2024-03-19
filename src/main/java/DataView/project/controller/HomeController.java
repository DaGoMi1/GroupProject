package DataView.project.controller;

import DataView.project.domain.Member;
import DataView.project.dto.RegistrationRequest;
import DataView.project.service.EmailService;
import DataView.project.service.MemberService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/home")
public class HomeController {

    private final EmailService emailService;
    private final MemberService memberService;

    public HomeController(EmailService emailService, MemberService memberService) {
        this.emailService = emailService;
        this.memberService = memberService;
    }
    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest request) {
        if (request.getPassword().equals(request.getPassword2())) {
            Member member = new Member();
            member.setUsername(request.getUsername());
            member.setPassword(request.getPassword());
            member.setName(request.getName());
            member.setEmail(request.getEmail());

            memberService.join(member);
            return "회원가입 성공";
        } else {
            return "회원가입 실패 (비밀번호 재확인 필요)";
        }
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String email) {
        try {
            // 이메일 보내기
            emailService.send(email);
            return "이메일 인증번호 전송 완료";
        } catch (Exception e) {
            return "이메일 인증번호 전송 실패: " + e.getMessage();
        }
    }

    @PostMapping("/check-authCode")
    public String checkAuthCode(@RequestParam String email, @RequestParam int number) {
        if (emailService.checkAuthCode(email, number)) {
            return "인증 완료";
        } else {
            return "인증 실패";
        }
    }
}
