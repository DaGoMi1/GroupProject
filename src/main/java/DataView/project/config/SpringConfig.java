package DataView.project.config;

import DataView.project.repository.SDJpaMemberRepository;
import DataView.project.service.EmailService;
import DataView.project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class SpringConfig {
    private final SDJpaMemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public SpringConfig(SDJpaMemberRepository memberRepository,
                        JavaMailSender javaMailSender) {
        this.memberRepository = memberRepository;
        this.javaMailSender = javaMailSender;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    @Bean
    public EmailService emailService(){
        return new EmailService(javaMailSender);
    }

}