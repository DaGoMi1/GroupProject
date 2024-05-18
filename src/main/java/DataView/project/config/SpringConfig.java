package DataView.project.config;

import DataView.project.repository.*;
import DataView.project.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {
    private final SDJpaMemberRepository memberRepository;
    private final SDJpaPostingRepository postingRepository;
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SDJpaCommentRepository commentRepository;
    private final SDJpaTimeTableRepository timeTableRepository;
    private final SDJpaSubjectRepository subjectRepository;
    private final SDJpaCourseRepository courseRepository;
    private final SDJpaSchedulesRepository schedulesRepository;
    private final SDJpaDataCreditRepository dataCreditRepository;
    private final SDJpaLiberalArtsCreditRepository liberalArtsCreditRepository;
    private final SDJpaGeneralEducationCurriculumRepository generalEducationCurriculumRepository;
    private final SDJpaCreditRepository creditRepository;
    private final SDJpaFileRepository fileRepository;

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository,
                timeTableRepository, bCryptPasswordEncoder);
    }

    @Bean
    public EmailService emailService() {
        return new EmailService(javaMailSender);
    }

    @Bean
    public PostingService postingService() {
        return new PostingService(postingRepository);
    }

    @Bean
    public CommentService commentService() {
        return new CommentService(commentRepository);
    }

    @Bean
    public FileService fileService(){
        return new FileService(fileRepository);
    }

    @Bean
    public TimeTableService timeTableService() {
        return new TimeTableService(timeTableRepository,
                subjectRepository, courseRepository, memberRepository);
    }

    @Bean
    public SchedulesService schedulesService() {
        return new SchedulesService(schedulesRepository, memberRepository);
    }

    @Bean
    public CreditService creditService() {
        return new CreditService(dataCreditRepository,
                liberalArtsCreditRepository, generalEducationCurriculumRepository, creditRepository);
    }

    @Bean
    public AdminService adminService() {
        return new AdminService(postingRepository, schedulesRepository, memberRepository);
    }

}