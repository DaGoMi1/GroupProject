package DataView.project.service;

import DataView.project.domain.Member;
import DataView.project.domain.TimeTable;
import DataView.project.dto.CustomUserDetails;
import DataView.project.dto.RegistrationRequest;
import DataView.project.repository.SDJpaMemberRepository;
import DataView.project.repository.SDJpaTimeTableRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class MemberService implements UserDetailsService {
    private final SDJpaMemberRepository memberRepository;
    private final SDJpaTimeTableRepository timeTableRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(SDJpaMemberRepository memberRepository,
                         SDJpaTimeTableRepository timeTableRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.timeTableRepository = timeTableRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (userDetails.member() != null) {
            return userDetails.member();
        } else {
            throw new UsernameNotFoundException("회원을 찾을 수가 없습니다.");
        }
    }

    public void join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_USER");

        memberRepository.save(member);

        for (int grade = 1; grade <= 6; grade++) {
            for (int semester = 1; semester <= 4; semester++) {
                TimeTable timeTable = getTimeTable(member, grade, semester);
                timeTableRepository.save(timeTable);
            }
        }
    }

    private static TimeTable getTimeTable(Member member, int grade, int semester) {
        TimeTable timeTable = new TimeTable();
        timeTable.setMember(member);
        timeTable.setGrade(grade);

        switch (semester) {
            case 1:
                timeTable.setSemester("first");
                break;
            case 2:
                timeTable.setSemester("summer");
                break;
            case 3:
                timeTable.setSemester("second");
                break;
            case 4:
                timeTable.setSemester("winter");
                break;
        }
        return timeTable;
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

    public void updatePassword(String username, String newPassword) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        member.setPassword(bCryptPasswordEncoder.encode(newPassword));

        memberRepository.save(member);
    }

    public boolean checkPassword(String password) {
        Member member = getMember();
        return bCryptPasswordEncoder.matches(password, member.getPassword());
    }

    public boolean matchPassword(String password, String password2) {
        return password.equals(password2);
    }

    public void deleteMember() {
        memberRepository.delete(getMember());
    }

    public Member mapByMemberRequest(RegistrationRequest request) {
        Member member = new Member();
        member.setUsername(request.getUsername());
        member.setPassword(request.getPassword());
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        return member;
    }

    public String findEmailByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return member.getEmail();
    }
}
