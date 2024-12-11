package com.buoi2.ltw.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origins}")
    private String[] allowedOrigins;

    @Value("${cors.allowed.methods}")
    private String[] allowedMethods;

    @Value("${cors.allowed.headers}")
    private String[] allowedHeaders;

    @Value("${cors.allow.credentials}")
    private boolean allowCredentials;

    @Configuration
    public class webConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") // Áp dụng cho tất cả các endpoint
                    .allowedOrigins("http://127.0.0.1:5500") // Chỉ định domain được phép
                    .allowedMethods("GET", "POST", "PUT", "DELETE") // Các method được phép
                    .allowedHeaders("*") // Cho phép tất cả các header
                    .allowCredentials(true); // Cho phép cookie, thông tin đăng nhập
        }
    }
}