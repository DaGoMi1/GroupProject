package DataView.project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class EmailService {
    private final JavaMailSender javaMailSender;
    private final Map<String, AuthInfo> dataMap = new ConcurrentHashMap<>();
    private static final long EXPIRATION_TIME_SECONDS = 180; // 3분

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(String email) throws MessagingException {
        String title = "한국해양대학교 Data Science 홈페이지 인증번호";
        int authCode = generateRandomCode(); // 랜덤 코드 생성
        String content = "인증번호는" + authCode + "입니다.";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(message, "UTF-8");

        mailHelper.setTo(email);
        mailHelper.setSubject(title);
        mailHelper.setText(content);

        dataMap.put(email, new AuthInfo(authCode, Instant.now()));

        javaMailSender.send(message);
    }

    private int generateRandomCode() {
        Random random = new Random();
        int min = (int) Math.pow(10, 6 - 1);
        int max = (int) Math.pow(10, 6) - 1;
        return random.nextInt(max - min + 1) + min;
    }

    public boolean checkAuthCode(String email, int number) {
        AuthInfo authInfo = dataMap.get(email);
        if (authInfo != null && authInfo.authCode() == number && isValid(authInfo.timestamp())) {
            dataMap.remove(email); // 인증번호 확인 후 삭제
            return true;
        }
        return false;
    }

    private boolean isValid(Instant timestamp) {
        return Instant.now().minusSeconds(EXPIRATION_TIME_SECONDS).isBefore(timestamp);
    }

    private record AuthInfo(int authCode, Instant timestamp) {
    }
}
