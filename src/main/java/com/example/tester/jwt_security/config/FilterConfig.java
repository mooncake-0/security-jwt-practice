package com.example.tester.jwt_security.config;

import com.example.tester.jwt_security.filter.MoimingJwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * SecurityFilterChain에 놓아도 작동하긴 할테지만,
 * 내가 나만의 큰 필터 체인을 하나 만드는 것
 * 굳이 SecurityFilterChain 을 건드리지 않는 것도 추천하는 방식이래
 *
 * MEMO: 참고로 해보면 알겠지만 SecurityFilterChain 이후에 내 필터 체인이 실행된다
 *       먼저 하게 하고 싶은 필터는 SFC 중 맨 처음에 있는 녀석들보다 addFilterBefore 하면 됨.
 *       SFC 의 Authorization 설정과 오류가 날 수도 있는지 여부를 확인하자!
 */

//@Configuration
public class FilterConfig {

//    @Bean
    public FilterRegistrationBean<MoimingJwtFilter> jwtFilter() {

        FilterRegistrationBean<MoimingJwtFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>(new MoimingJwtFilter());

        filterFilterRegistrationBean.addUrlPatterns("/*"); //모든 요청
        filterFilterRegistrationBean.setOrder(0);

        return filterFilterRegistrationBean;
    }
}
