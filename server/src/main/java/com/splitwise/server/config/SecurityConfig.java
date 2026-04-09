package com.splitwise.server.config;

import tools.jackson.databind.ObjectMapper;
import com.splitwise.server.common.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    public SecurityConfig(@Qualifier("appObjectMapper") ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/health").permitAll()
                .anyRequest().authenticated()
            )
            // 401 - Chưa đăng nhập, trả JSON thay vì redirect login page
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    ApiResponse<Void> body = ApiResponse.error(
                            401, "Bạn chưa đăng nhập hoặc không phải người dùng trong hệ thống."
                    );
                    PrintWriter writer = response.getWriter();
                    writer.write(objectMapper.writeValueAsString(body));
                    writer.flush();
                })
                // 403 - Đã đăng nhập nhưng không có quyền
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    ApiResponse<Void> body = ApiResponse.error(
                            403, "Bạn không có quyền truy cập tài nguyên này."
                    );
                    PrintWriter writer = response.getWriter();
                    writer.write(objectMapper.writeValueAsString(body));
                    writer.flush();
                })
            );

        return http.build();
    }
}
