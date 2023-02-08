package com.example.tester.security;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


//@EnableWebSecurity
//@RequiredArgsConstructor
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

//    private final PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        String password = passwordEncoder.encode("1111");

//        auth.inMemoryAuthentication().withUser("user").password(password).authorities("USER");
//        auth.inMemoryAuthentication().withUser("manager").password(password).authorities("MANAGER");
//        auth.inMemoryAuthentication().withUser("admin").password(password).authorities("ADMIN");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        // css, js 등의 자원 파일들에 대한 인가 처리는 모두 무시 (페이지 내에서 자원은 따로 관리하지 않는다, 경로 및 페이지만!)
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests() // 권한 부여를 설정할 것이다
                .antMatchers("/", "/users").permitAll() // 인가 예외
                .antMatchers("/mypage").hasRole("USER")
                .antMatchers("/messages").hasRole("MANAGER")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()
        ;


        http
                .formLogin();

    }
}
