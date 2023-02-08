package com.example.tester.jwt_security.config;

import com.example.tester.jwt_security.filter.JwtAuthenticationFilter;
import com.example.tester.jwt_security.filter.JwtAuthorizationFilter;
import com.example.tester.jwt_security.filter.MoimingJwtFilter;
import com.example.tester.jwt_security.handler.JwtAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {


    private final CorsFilter corsFilter;

//    private final MoimingJwtFilter jwtFilter;

    @Bean
    public JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler() {
        return new JwtAuthenticationSuccessHandler();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler());

        return jwtAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {

        return new JwtAuthorizationFilter(authenticationManagerBean());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // JWT 기본 설정 기존 HTTP 규약을 사용하면 안됨
        http.csrf().disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용안함
        ;

        http.formLogin().disable(); // FORM 태그를 활용한 로그인 방식을 사용하지 않겠다
        http.httpBasic().disable(); // http basic 을 사용한 인증을 하지 않겠다. 베리어 방식을 쓰겠다

        // http basic 이란
        // 기본적으로 http-only 를 통해 form 제외 방식을 받지 않는 것에서 벗어난 방식
        // Header: Authorization: Id /Pw 를 등재하여 지속적으로 통신을 하는 방식
        // 노출시 risk 가 너무 크기 때문에 Bearer 토큰 방식을 사용하겠다고 설정하는 것
        // JWT 는 Bearer 방식을 활용함. 당연히 노출되면 안좋지만 risk 가 그래도 더 낮다.(유효시간도 있고,암호화도 더 확실하기 때문)

        http.addFilter(corsFilter); // 이 설정에 대해선 생각해보자
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // Custom App 설정

        http
                .authorizeRequests()
                .antMatchers("/api/v1/user/**").hasAnyRole("MANAGER", "ADMIN", "USER")
                .antMatchers("/api/v1/manager/**").hasAnyRole("USER", "MANAGER")
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        ;


        http
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter());


        // 이제부터 찐 JWT 설정


    }


}
