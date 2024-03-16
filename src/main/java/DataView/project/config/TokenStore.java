package DataView.project.config;

import DataView.project.dto.JwtToken;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenStore {

    private final Set<JwtToken> validTokens = new HashSet<>();

    // 토큰 저장
    public void storeToken(JwtToken token) {
        validTokens.add(token);
    }

    // 토큰 제거
    public void removeToken(JwtToken token) {
        validTokens.remove(token);
    }

    // 토큰 유효성 검사
    public boolean isValidToken(JwtToken token) {
        return validTokens.contains(token);
    }
}
