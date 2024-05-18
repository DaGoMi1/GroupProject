package DataView.project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;
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

    public void sendAuthCode(String email) throws MessagingException {
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

        if (email == null || number == 0) {
            throw new NullPointerException("요청한 변수가 부족합니다.");
        } else if (authInfo != null && authInfo.authCode() == number && isValid(authInfo.timestamp())) {
            dataMap.remove(email); // 인증번호 확인 후 삭제
            return true;
        } else {
            return false;
        }
    }

    private boolean isValid(Instant timestamp) {
        return Instant.now().minusSeconds(EXPIRATION_TIME_SECONDS).isBefore(timestamp);
    }

    private record AuthInfo(int authCode, Instant timestamp) {
    }

    public String sendTemporaryPassword(String email) throws MessagingException {
        String title = "한국해양대학교 Data Science 홈페이지 임시 비밀번호";
        String temporaryPassword = generateRandomPassword(); // 랜덤 비밀번호 생성
        String content = "임시 비밀번호는 " + temporaryPassword + " 입니다.";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(message, "UTF-8");

        // 여기서 email 변수가 어디에서 온 것인지 확인해주세요.
        mailHelper.setTo(email);
        mailHelper.setSubject(title);
        mailHelper.setText(content);

        javaMailSender.send(message);

        return temporaryPassword;
    }

    private String generateRandomPassword() {
        // 랜덤한 8자리 문자열 생성 (숫자와 문자 모두 포함)
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
