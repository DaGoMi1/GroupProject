package DataView.project.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/home/user", "/home/user/login", "/home/send-email", "/home/check/authCode"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http
                .formLogin((auth) -> auth.loginProcessingUrl("/home/user/login")
                        .successHandler((request, response, authentication) -> { // 로그인 성공 시 핸들러
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("{\"message\": \"success\"}");
                        })
                        .failureHandler((request, response, exception) -> { // 로그인 실패 시 핸들러
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"message\": \"fail\"}");
                        })
                );

        http
                .logout((auth) -> auth
                        .logoutUrl("/home/user/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        http
                .sessionManagement((auth) -> auth
                        // 세션 최대 허용 시간 설정 (기본값: 30분)
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/login?expired=true") // 만료된 세션 리다이렉션 URL 설정
                        .sessionFixation().migrateSession() // 세션 고정 방지를 위한 설정
                        .maximumSessions(1) // 동시 세션 허용 개수 설정
                        .maxSessionsPreventsLogin(false) // 동시 로그인 차단 여부 설정
                        .expiredUrl("/login?expired=true") // 만료된 세션 리다이렉션 URL 설정
                );
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());

        http
                .csrf(AbstractHttpConfigurer::disable);

        http
                .cors(auth -> auth.configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http:~~"));
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowCredentials(true);
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setMaxAge(3600L);
                            return config;
                        })
                );

        http
                .exceptionHandling(configurer -> configurer
                        .accessDeniedPage("/error/access-denied"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}