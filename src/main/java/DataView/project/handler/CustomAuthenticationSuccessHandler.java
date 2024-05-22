package DataView.project.handler;

import DataView.project.dto.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        System.out.println("세션 정보: " + request.getSession(false).getId());
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 학번과 이름을 담는 Map 생성
        Map<String, String> memberInfo = new HashMap<>();
        memberInfo.put("studentId", userDetails.getUsername());
        memberInfo.put("name", userDetails.getName());
        
        // 응답 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        // Map을 JSON 형식으로 변환하여 응답 본문에 작성
        response.getWriter().write(objectMapper.writeValueAsString(memberInfo));
        System.out.println(objectMapper.writeValueAsString(memberInfo));
    }
}
