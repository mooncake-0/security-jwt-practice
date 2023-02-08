package com.example.tester.jwt_security.filter;

import com.example.tester.jwt_security.auth.PrincipalDetails;
import com.example.tester.jwt_security.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


// security 가 등재되면 자연스럽게 SpringFilterProxy에 등재되는 필터
// 폼 로그인을 껐기 때문에 UsernamePasswordAuthFilter는 작동하지 않는다, .
// 그래서 이거를 다시 등록하되, 나만의 필터로 등록할 것임.
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    // login 요청을 하면 수행을 하는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("Attempting Authentication");

        ObjectMapper om = new ObjectMapper();

        try {

            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println("user = " + user);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            //loadUserName이 실행된다
            Authentication authentication
                    = getAuthenticationManager().authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("principalDetails = " + principalDetails.getUser().getUsername());
            System.out.println("principalDetailsAuth = " + principalDetails.getAuthorities());


            // MEMO: Filter 단에서 authentication 을 return 해주는 수행을 하게되면 session 영역에 authentication 을 저장하게 된다.
            //       SecurityContextHolder > SecurityContext > Authentication
            //       지금까지 잘 이해했다면 이상하다고 생각해야함. 왜냐하면 JWT의 장점은 Session 저장의 부담을 없애고 토큰 그 자체로만 활용하기 위함
            //       단지 편하려고 사용하는 거임. Role 을 Security 가 처리해주니까. 진짜 쓰려면 Authorization 마저 Custom 해야함

            return authentication;

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
