package com.example.tester.jwt_security.config;

// 공유시키면 안되는 서버 정보

public interface JwtProperties {

    String JWT_SECRET = "moiming";
    int EXPIRATION_TIME = 60000 * 10; // 10분
    String HEADER_ACCESS_TOKEN = "ACCESS_TOKEN";

}
