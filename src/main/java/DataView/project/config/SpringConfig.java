package DataView.project.config;

import DataView.project.repository.SDJpaMemberRepository;
import DataView.project.service.EmailService;
import DataView.project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringConfig {
    private final SDJpaMemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SpringConfig(SDJpaMemberRepository memberRepository,
                        JavaMailSender javaMailSender,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        AuthenticationManagerBuilder authenticationManagerBuilder,
                        JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.javaMailSender = javaMailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository,
                bCryptPasswordEncoder, authenticationManagerBuilder, jwtTokenProvider);
    }

    @Bean
    public EmailService emailService(){
        return new EmailService(javaMailSender);
    }

}