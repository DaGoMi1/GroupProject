package DataView.project.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 로그인 실패 시 403 Forbidden 응답을 클라이언트에게 반환
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8"); // 인코딩 설정
        response.setContentType("text/plain;charset=UTF-8"); // Content-Type 설정
        response.getWriter().println("로그인 실패"); // 실패 메시지 추가 가능
        response.getWriter().flush();
    }
}
