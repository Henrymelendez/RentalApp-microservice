package com.rentalApp.auth.services.impl;

import com.rentalApp.auth.exceptions.InvalidTokenException;
import com.rentalApp.auth.model.User;
import com.rentalApp.auth.services.AuthSigningKeyResolver;
import com.rentalApp.auth.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private AuthSigningKeyResolver authSigningKeyResolver;
    @Override
    public void validateToken(String jwtToken) throws InvalidTokenException {
        try {
            Jwts.parserBuilder()
                    .setSigningKeyResolver(authSigningKeyResolver)
                    .build()
                    .parse(jwtToken);
        }
        catch(ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            throw new InvalidTokenException("Invalid Token ", e);
        }

    }

    @Override
    public void generateToken(User user) {
        String jwtToken;
        jwtToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setAudience(user.getRoles().toString())
                .setIssuer(user.getId())
                .signWith(authSigningKeyResolver.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
        user.setJwtToken(jwtToken);

    }
}
