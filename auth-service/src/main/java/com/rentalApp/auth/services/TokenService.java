package com.rentalApp.auth.services;

import com.rentalApp.auth.exceptions.InvalidTokenException;
import com.rentalApp.auth.model.User;

public interface TokenService {

    void validateToken(String jwtToken) throws InvalidTokenException;
    void generateToken(User user);
}
