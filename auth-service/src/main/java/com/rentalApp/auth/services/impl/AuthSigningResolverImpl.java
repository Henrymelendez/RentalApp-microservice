package com.rentalApp.auth.services.impl;

import com.rentalApp.auth.services.AuthSigningKeyResolver;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;

import static java.util.Objects.isNull;

@Component
public class AuthSigningResolverImpl implements AuthSigningKeyResolver {

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    private SecretKey secretKey;

    @Override
    public SecretKey getSecretKey() {

        if(isNull(secretKey)){
            this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(this.secretKeyString.getBytes()));
        }

        return this.secretKey;
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        return getSecretKey();
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, String s) {
        return getSecretKey();
    }
}
