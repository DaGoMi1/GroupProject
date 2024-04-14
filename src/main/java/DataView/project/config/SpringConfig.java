package DataView.project.config;

import DataView.project.repository.*;
import DataView.project.service.*;
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
    private final SDJpaCommentRepository commentRepository;
    private final SDJpaTimeTableRepository timeTableRepository;
    private final SDJpaSubjectRepository subjectRepository;
    private final SDJpaCourseRepository courseRepository;
    private final SDJpaSchedulesRepository schedulesRepository;
    private final SDJpaDataCreditRepository dataCreditRepository;
    private final SDJpaLiberalArtsCreditRepository liberalArtsCreditRepository;
    private final SDJpaGeneralEducationCurriculumRepository generalEducationCurriculumRepository;

    @Autowired
    public SpringConfig(SDJpaMemberRepository memberRepository,
                        SDJpaPostingRepository postingRepository,
                        JavaMailSender javaMailSender,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        SDJpaCommentRepository commentRepository,
                        SDJpaTimeTableRepository timeTableRepository,
                        SDJpaSubjectRepository subjectRepository,
                        SDJpaCourseRepository courseRepository,
                        SDJpaSchedulesRepository schedulesRepository,
                        SDJpaDataCreditRepository dataCreditRepository,
                        SDJpaLiberalArtsCreditRepository liberalArtsCreditRepository,
                        SDJpaGeneralEducationCurriculumRepository generalEducationCurriculumRepository) {
        this.memberRepository = memberRepository;
        this.postingRepository = postingRepository;
        this.javaMailSender = javaMailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.commentRepository = commentRepository;
        this.timeTableRepository = timeTableRepository;
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
        this.schedulesRepository = schedulesRepository;
        this.dataCreditRepository = dataCreditRepository;
        this.liberalArtsCreditRepository = liberalArtsCreditRepository;
        this.generalEducationCurriculumRepository = generalEducationCurriculumRepository;
    }

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
                liberalArtsCreditRepository, generalEducationCurriculumRepository);
    }
}