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

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(SDJpaMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증

        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_USER");
        if (member.getUserNumber().equals("DSYJ")) {
            member.setRole("ROLE_ADMIN");
        }

        memberRepository.save(member);
    }

    public boolean isPasswordMatching(String password, String password2) {
        return password.equals(password2);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByUserNumber(member.getUserNumber())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserNumber(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new CustomUserDetails(member);
    }

    @Transactional
    public void updateMember(Member member) {
        Member existingMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        existingMember.setName(member.getName());
        existingMember.setEmail(member.getEmail());
        existingMember.setPassword(member.getPassword());

        memberRepository.save(existingMember);
    }

    @Transactional
    public void updatePassword(String username, String newPassword) {
        Member member = memberRepository.findByUserNumber(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        member.setPassword(bCryptPasswordEncoder.encode(newPassword));

        memberRepository.save(member);
    }
}
