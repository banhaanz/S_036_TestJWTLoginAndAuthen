package com.demo.testjwt.config.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenManager {
    private JWTPayload jwtPayload;

    public void setJWTPayload(JWTPayload userDetailInHttpHeader) {
        this.jwtPayload = userDetailInHttpHeader;
    }

    public JWTPayload getJWTPayload() {
        return this.jwtPayload;
    }
}
