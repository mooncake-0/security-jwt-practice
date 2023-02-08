package com.example.tester.jwt_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // 자바 스크립트를 통해 요청이 들어왔을 때 응답하지 않는다
        corsConfiguration.addAllowedOrigin("*"); // 모든 ip에 대한 응답 허용
        corsConfiguration.addAllowedHeader("*"); // 모든 header에 응답 허용
        corsConfiguration.addAllowedMethod("*"); // 모든 P,G,U,D 에 대한 응답 허용

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/api/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
