package com.splitwise.server.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * ObjectMapper dùng khi cần ghi JSON thủ công (ví dụ SecurityConfig).
 * Spring Boot 4.x dùng Jackson 3.x (tools.jackson); không dùng {@code spring.jackson.serialization.write-dates-as-timestamps}
 * — enum đó không còn trong {@code SerializationFeature}.
 */
@Configuration
public class JacksonConfig {

    @Bean("appObjectMapper")
    public ObjectMapper objectMapper() {
        // JsonMapper là implementation JSON cụ thể của ObjectMapper trong Jackson 3.x
        return JsonMapper.builder().build();
    }
}
