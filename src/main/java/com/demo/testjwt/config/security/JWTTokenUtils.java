package com.demo.testjwt.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class JWTTokenUtils {
    static final long EXPIRATION_TIME = 1000 * 60; // 60 seconds timeout
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";
    static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    static void addAuthentication(HttpServletResponse res, String username) throws IOException {
        Date expireTime = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(expireTime)
                .signWith(SECRET_KEY)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);

        AuthenBody authenBody = AuthenBody.builder()
                .tokenExpire(expireTime.toString())
                .build();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(new ObjectMapper().writeValueAsString(authenBody));

    }

    static Authentication getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(HEADER_STRING);
        if(token == null) {
            return null;
        }

        String username = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        return username != null ? new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()) : null;
    }
}
