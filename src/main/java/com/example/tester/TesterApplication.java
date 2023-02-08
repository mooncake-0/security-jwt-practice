package com.example.tester;

import com.example.tester.jwt_security.filter.MoimingJwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TesterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TesterApplication.class, args);
    }

//    @Bean
//    public MoimingJwtFilter moimingJwtFilter() {
//        return new MoimingJwtFilter();
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 빈 따로 깔아줘야하는데 그냥 귀찮아서 Main 실행 단에서 빈 주입합니다.
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

}
