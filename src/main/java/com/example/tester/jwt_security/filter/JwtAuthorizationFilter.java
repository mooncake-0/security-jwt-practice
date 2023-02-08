package com.example.tester.jwt_security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.tester.jwt_security.auth.PrincipalDetails;
import com.example.tester.jwt_security.domain.User;
import com.example.tester.jwt_security.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인증 이후 요청 단에서, JWT 토큰을 검증해보자
// 권한이나 인증이 필요할 때 타는 필터인 BasicAuthenticationFilter 를 사용해보자.

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("인증이나 권한이 필요한 요청이 들어옴");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader = " + jwtHeader);

        // JWT 토큰을 검증해서 정상인지 확인해야 함
        if (!jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        if (!StringUtils.hasText(jwtHeader)) {
            chain.doFilter(request, response);
            return;
        }

        String userJwtToken = jwtHeader.replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC512("moiming"))
                .build()
                .verify(userJwtToken) // 토큰을 검증해서 성공하면 claim을 가져와보자
                .getClaim("username")
                .asString();

        // 서명이 정상적으로 진행되었다
        if (StringUtils.hasText(username)) {
            User userByUsername = userRepository.findUserByUsername(username);

            // 이후 필터에서 [검증된 객체]임을 알고, ROLE이 주입된 상태의 Authentication 객체를 전달해야함
            PrincipalDetails principalDetails = new PrincipalDetails(userByUsername);

            System.out.println("principalDetails = " + principalDetails.toString());

            // JWT 토큰 서명을 통해서 만든 객체 (서명이 정상이면 Authentication 객체를 강제로 만들어 줍니다)
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails
                    , null, principalDetails.getAuthorities());

            // 강제로 Context 에 저장한다 // 이거는 좀 의아하긴 함
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }else{
            System.out.println("검증에 실패한 토큰입니다");
            return;
        }

    }
}
