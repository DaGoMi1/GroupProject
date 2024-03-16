package DataView.project.controller;

import DataView.project.config.TokenStore;
import DataView.project.domain.Member;
import DataView.project.dto.JwtToken;
import DataView.project.dto.RegistrationRequest;
import DataView.project.service.EmailService;
import DataView.project.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/home")
public class HomeController {

    private final EmailService emailService;
    private final MemberService memberService;
    private final TokenStore tokenStore;

    public HomeController(EmailService emailService,
                          MemberService memberService,
                          TokenStore tokenStore) {
        this.emailService = emailService;
        this.memberService = memberService;
        this.tokenStore = tokenStore;
    }

    @GetMapping("")
    public String home() {
        return "home";
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
    @ResponseBody
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

    @PostMapping("/user/login")
    @ResponseBody
    public Object login(@RequestParam String username, @RequestParam String password) {
        try {
            return memberService.signIn(username, password); // 또는 JWT 토큰을 반환하는 것이 좋습니다.
        } catch (Exception e) {
            return "로그인 실패: " + e.getMessage();
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 사용자의 토큰을 추출합니다. 토큰을 추출하는 방법은 애플리케이션의 인증 방식에 따라 다를 수 있습니다.
        JwtToken token = extractTokenFromRequest(request);

        // 토큰이 null이 아니고 유효한 경우에만 토큰을 제거합니다.
        if (token != null && tokenStore.isValidToken(token)) {
            tokenStore.removeToken(token);
            return "로그아웃 되었습니다.";
        } else {
            return "유효하지 않은 토큰 또는 이미 로그아웃된 사용자입니다.";
        }
    }

    // HttpServletRequest에서 토큰을 추출하는 메서드
    private JwtToken extractTokenFromRequest(HttpServletRequest request) {
        // HTTP 요청에서 Authorization 헤더를 가져옵니다.
        String authHeader = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 형식이 올바르지 않은 경우 null을 반환합니다.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        // Bearer 스키마를 제거하여 토큰만 추출합니다.
        String tokenValue = authHeader.substring(7);
        return new JwtToken("Bearer", tokenValue, null); // Refresh Token은 사용되지 않으므로 null로 설정합니다.
    }
}
