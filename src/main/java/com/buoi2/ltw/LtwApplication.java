package com.buoi2.ltw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication

public class LtwApplication {

    public static void main(String[] args) {
        SpringApplication.run(LtwApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Cho phép CORS cho tất cả các đường dẫn và tất cả các phương thức từ http://localhost
                registry.addMapping("/**")  // Cấu hình cho tất cả các API
                        .allowedOrigins("http://127.0.0.1:5500")  // Cho phép yêu cầu từ http://localhost:3000
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Cho phép tất cả các phương thức HTTP
                        .allowedHeaders("*")  // Cho phép tất cả các header
                        .allowCredentials(true);  // Cho phép gửi cookie và thông tin xác thực
            }
        };
    }
}
