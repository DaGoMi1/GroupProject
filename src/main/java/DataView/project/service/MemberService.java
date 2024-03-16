package DataView.project.service;

import DataView.project.config.JwtTokenProvider;
import DataView.project.domain.Member;
import DataView.project.dto.CustomUserDetails;
import DataView.project.dto.JwtToken;
import DataView.project.repository.SDJpaMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

public class MemberService implements UserDetailsService {
    private final SDJpaMemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(SDJpaMemberRepository memberRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder,
                         AuthenticationManagerBuilder authenticationManagerBuilder,
                         JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.
                getObject().authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    public void join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));

        // 사용자의 기본 권한 설정
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        // 특정 사용자에게 추가 권한 부여
        if (member.getUsername().equals("DSYJ")) {
            roles.add("ROLE_ADMIN");
        }

        member.setRoles(roles);

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
}
