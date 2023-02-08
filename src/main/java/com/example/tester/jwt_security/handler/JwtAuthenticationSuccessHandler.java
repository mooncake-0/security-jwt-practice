package com.example.tester.jwt_security.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.tester.jwt_security.auth.PrincipalDetails;
import com.example.tester.jwt_security.config.JwtProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// JWT Token 처리

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // RSA 를 사용하지 않고, HASH 암호 방식을 더 많이 사용

        String jwtToken = JWT.create()
                .withSubject("Moiming Jwt Token") // 토큰의 이름
//                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10))) // 10분
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUser().getId()) // 클레임은 원하는 정보 다 담으면 됨
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.JWT_SECRET));
//                .sign(Algorithm.HMAC512("moiming"));


//        response.addHeader("ACCESS_TOKEN", jwtToken);
        response.addHeader(JwtProperties.HEADER_ACCESS_TOKEN, jwtToken);
    }
}
