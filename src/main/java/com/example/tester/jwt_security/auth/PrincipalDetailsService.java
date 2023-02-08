package com.example.tester.jwt_security.auth;

import com.example.tester.jwt_security.domain.User;
import com.example.tester.jwt_security.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // DB에서 이름에 따른 유저 정보를 가져온다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("PrincipalDetailService 의 유저 ID 확인 ");

        User userByUsername = userRepository.findUserByUsername(username);

        return new PrincipalDetails(userByUsername);
    }

}
