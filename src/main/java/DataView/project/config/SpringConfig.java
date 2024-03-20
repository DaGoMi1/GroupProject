package DataView.project.config;

import DataView.project.repository.SDJpaMemberRepository;
import DataView.project.repository.SDJpaPostingRepository;
import DataView.project.service.EmailService;
import DataView.project.service.MemberService;
import DataView.project.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringConfig {
    private final SDJpaMemberRepository memberRepository;
    private final SDJpaPostingRepository postingRepository;
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SpringConfig(SDJpaMemberRepository memberRepository,
                        SDJpaPostingRepository postingRepository,
                        JavaMailSender javaMailSender,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.postingRepository = postingRepository;
        this.javaMailSender = javaMailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository, bCryptPasswordEncoder);
    }

    @Bean
    public EmailService emailService(){
        return new EmailService(javaMailSender);
    }

    @Bean
    public PostingService postingService(){
        return new PostingService(postingRepository);
    }


}