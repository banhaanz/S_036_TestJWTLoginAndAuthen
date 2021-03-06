package com.demo.testjwt.config.security;

import com.demo.testjwt.model.UserDetailM;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenFilter extends AbstractAuthenticationProcessingFilter {

    public JWTAuthenFilter(String url, AuthenticationManager manager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(manager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        UserDetailM userDetailM = new ObjectMapper().readValue(httpServletRequest.getInputStream(),UserDetailM.class);
        return getAuthenticationManager()
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userDetailM.getUsername(),
                                userDetailM.getPassword(),
                                Collections.emptyList()
                        )
                );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JWTTokenUtils.addAuthentication(response, authResult.getName());
    }
}
