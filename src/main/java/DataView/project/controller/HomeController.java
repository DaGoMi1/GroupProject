package DataView.project.controller;

import DataView.project.domain.Member;
import DataView.project.dto.CustomUserDetails;
import DataView.project.dto.PasswordRequest;
import DataView.project.dto.RegistrationRequest;
import DataView.project.service.EmailService;
import DataView.project.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/check/authCode")
    public String checkAuthCode(@RequestParam String email, @RequestParam int number) {
        if (emailService.checkAuthCode(email, number)) {
            return "인증 완료";
        } else {
            return "인증 실패";
        }
    }

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordRequest request) {

        // 현재 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 현재 비밀번호 확인
        if (!userDetails.getPassword().equals(request.getCurrentPassword())) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("현재 비밀번호 인증 실패");
        }

        // 새 비밀번호와 재확인 비밀번호 일치 확인
        if (!request.getNewPassword().equals(request.getCheckPassword())) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("새로운 비밀번호 2개 불일치");
        }

        // 새 비밀번호 설정
        memberService.updatePassword(userDetails.getUsername(), request.getNewPassword());

        return ResponseEntity.ok().body("비밀번호 변경 성공");
    }
}
