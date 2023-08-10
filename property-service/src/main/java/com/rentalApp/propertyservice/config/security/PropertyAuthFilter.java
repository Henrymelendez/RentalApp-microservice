package com.rentalApp.propertyservice.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.ObjectUtils.isEmpty;

public class PropertyAuthFilter extends AbstractAuthenticationProcessingFilter {


    public PropertyAuthFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public PropertyAuthFilter(RequestMatcher requestMatcher){
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String tokenUnStripped = request.getHeader(AUTHORIZATION);
        String token = StringUtils.removeStart(Optional.ofNullable(tokenUnStripped).orElse(""), "Bearer").trim();
        Authentication authentication;
        if(isEmpty(token)){
            authentication = new UsernamePasswordAuthenticationToken("guest", "");
        }
        else {
            authentication = new UsernamePasswordAuthenticationToken("user", token);
        }
        return getAuthenticationManager().authenticate(authentication);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request,response);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", failed.getCause());
        jsonObject.put("error Message", failed.getMessage());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject.toString());
        response.getWriter().flush();
    }
}