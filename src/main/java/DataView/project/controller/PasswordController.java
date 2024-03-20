package DataView.project.controller;

import DataView.project.service.MemberService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PasswordController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/changepassword")
    public String changePasswordForm(Model model) {
        model.addAttribute("passwordForm", new PasswordForm());
        return "change-password-form";
    }

    @PostMapping("/changepassword")
    public String changePassword(@ModelAttribute("passwordForm") PasswordForm passwordForm, BindingResult bindingResult, Model model) {
        // 유효성 검사
        if (bindingResult.hasErrors()) {
            return "change-password-form";
        }

        // 현재 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserDetails currentUser = memberService.loadUserByUsername(currentPrincipalName);

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(passwordForm.getCurrentPassword(), currentUser.getPassword())) {
            model.addAttribute("currentPasswordError", true);
            return "change-password-form";
        }

        // 새 비밀번호와 재확인 비밀번호 일치 확인
        if (!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
            model.addAttribute("passwordMismatchError", true);
            return "change-password-form";
        }

        // 새 비밀번호 설정
        memberService.updatePassword(currentPrincipalName, passwordForm.getNewPassword());

        return "redirect:/home";
    }
}