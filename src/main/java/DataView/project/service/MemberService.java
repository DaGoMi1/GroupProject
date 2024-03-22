package DataView.project.service;

import DataView.project.domain.Member;
import DataView.project.dto.CustomUserDetails;
import DataView.project.repository.SDJpaMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class MemberService implements UserDetailsService {
    private final SDJpaMemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(SDJpaMemberRepository memberRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));

        String role;

        // 특정 사용자에게 추가 권한 부여
        if (member.getUsername().equals("DSYJ")) {
            role = "ROLE_ADMIN";
        } else {
            role = "ROLE_USER";
        }

        member.setRole(role);

        memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByUsername(member.getUsername())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new CustomUserDetails(member);
    }
    @Transactional
    public void updatePassword(String username, String newPassword) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        member.setPassword(bCryptPasswordEncoder.encode(newPassword));

        memberRepository.save(member);
    }

}
