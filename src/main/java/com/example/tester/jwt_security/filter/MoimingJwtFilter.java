package com.example.tester.jwt_security.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MoimingJwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("JWT 필터 ㄲ");

        // 필터 이동 전처리
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        if (servletRequest.getMethod().equals("POST")) {
            String headerAuth = servletRequest.getHeader("Authorization");
            System.out.println("headerAuth = " + headerAuth);

            // MEMO:: 사실상 토큰을 검증해주는 로직이 들어올 것임
            if (headerAuth.equals("YOUR_JWT_TOKEN")) {
                // 검증되면 doFilter 진행, 안되면 Authentication Exception 발행
                chain.doFilter(request, response); // 다음 필터로 이동
            }else{
                PrintWriter writer = servletResponse.getWriter();
                writer.println("Authentication Failed, Wrong Jwt Token");
            }
        }

    }
}
