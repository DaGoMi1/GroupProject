package DataView.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/login_process",
                                "/", "/home", "/home/send-email"
                        ).permitAll()
                        .requestMatchers("/notice/write", "/notice/setting",
                                "/notice/voteStart").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http
                .formLogin((auth) -> auth.loginPage("/home") //우리가 만들어서 쓸 로그인 페이지 경로
                        .loginProcessingUrl("/user/login") //로그인 요청 받을 주소
                        .failureUrl("/home?error=true")
                        .defaultSuccessUrl("/main")
                        .successHandler((request, response, authentication) ->
                                response.sendRedirect("/main"))
                        .permitAll()
                );

        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());

        http
                .csrf(AbstractHttpConfigurer::disable);

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