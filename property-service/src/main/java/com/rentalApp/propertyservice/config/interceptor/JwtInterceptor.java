package com.rentalApp.propertyservice.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final String AUTH_SERVICE_URL = "http://auth-service/token/validate";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AUTHORIZATION);

        if(token == null || !token.startsWith("Bearer ")){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token missing");
            return false;
        }

        token.substring(7);
        System.out.println(token);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(token);
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> validResponse = new RestTemplate().exchange(
                    AUTH_SERVICE_URL,
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            );

            if(validResponse.getStatusCode() != HttpStatus.OK){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalid");
                return false;
            }
        } catch (HttpClientErrorException e){
            response.sendError(e.getStatusCode().value(), "Token Validation failed");
            return false;
        }
        return true;
    }
}
