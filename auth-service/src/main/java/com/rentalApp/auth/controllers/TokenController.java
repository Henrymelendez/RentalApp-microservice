package com.rentalApp.auth.controllers;

import com.rentalApp.auth.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.ObjectUtils.isEmpty;


@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    TokenService tokenService;

    @PreAuthorize("hasAnyRole('ANONYMOUS','USER','ADMIN'")
    @GetMapping("/validate")
    public void validateToken(HttpServletRequest httpServletRequest) throws Exception{
        String authHeader = httpServletRequest.getHeader(AUTHORIZATION);
        String token = null;
        if(!isEmpty(authHeader)){
            token = authHeader.split("\\s")[1];
        }

        tokenService.validateToken(token);
    }
}
