package DataView.project.controller;

import DataView.project.domain.Member;
import DataView.project.dto.*;
import DataView.project.service.EmailService;
import DataView.project.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/home")
public class HomeController {

    private final EmailService emailService;
    private final MemberService memberService;

    public HomeController(EmailService emailService,
                          MemberService memberService) {
        this.emailService = emailService;
        this.memberService = memberService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            if (memberService.matchPassword(request.getPassword(), request.getPassword2())) { // 패스워드가 같다면
                Member member = memberService.mapByMemberRequest(request); // request 내용 Member 객체에 담고
                memberService.join(member); // DB 저장
                return ResponseEntity.ok("회원가입 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 불일치"); // 패스워드가 다르다면
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패 " + e.getMessage());
        }
    }

    @PostMapping("/send/email")
    public ResponseEntity<?> sendEmail(@RequestBody RegistrationRequest request) {
        try {
            emailService.sendAuthCode(request.getEmail()); // 이메일 보내기
            return ResponseEntity.ok().body("이메일 전송 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송 실패");
        }
    }

    @PostMapping("/check/authCode")
    public ResponseEntity<?> checkAuthCode(@RequestBody AuthCodeDTO request) {
        try {
            if (emailService.checkAuthCode(request.getEmail(), request.getNumber())) { // 인증코드가 일치한다면
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패"); // 인증코드가 불일치한다면
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증할 수 없습니다: " + e.getMessage());
        }
    }

    @PatchMapping("/change/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordRequest request) {
        try {
            // 현재 사용자 가져오기
            Member member = memberService.getMember();

            // 현재 비밀번호 확인
            if (!memberService.checkPassword(request.getCurrentPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재 비밀번호 인증 실패");
            }

            // 새 비밀번호와 재확인 비밀번호 일치 확인
            if (!request.getNewPassword().equals(request.getCheckPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("새로운 비밀번호 2개 불일치");
            }

            // 새 비밀번호 설정
            memberService.updatePassword(member.getUsername(), request.getNewPassword());

            return ResponseEntity.ok().body("비밀번호 변경 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버측 오류 " + e.getMessage());
        }
    }

    @DeleteMapping("/withdrawal")
    public ResponseEntity<?> memberWithdrawal(@RequestBody RegistrationRequest request) {
        try {
            if (memberService.checkPassword(request.getPassword())) { // 비밀번호가 맞다면
                memberService.deleteMember(); // 회원탈퇴
                return ResponseEntity.ok("회원탈퇴 성공");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 확인 실패"); //비밀번호가 다르다면
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/name")
    public ResponseEntity<?> getMemberName() {
        try{
            String name = memberService.getMember().getName();
            return ResponseEntity.ok(name);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/student-id")
    public ResponseEntity<?> getStudentId() {
        try{
            String studentId = memberService.getMember().getUsername();
            return ResponseEntity.ok(studentId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/send/password")
    public ResponseEntity<?> sendPassword(@RequestBody RegistrationRequest request) {
        try{
            String email = memberService.findEmailByUsername(request.getUsername());
            String temporaryPassword = emailService.sendTemporaryPassword(email);
            memberService.updatePassword(request.getUsername(), temporaryPassword);
            return ResponseEntity.ok("임시 비밀번호 발송 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
