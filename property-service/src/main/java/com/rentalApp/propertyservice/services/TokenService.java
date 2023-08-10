package com.rentalApp.propertyservice.services;

import java.util.List;

public interface TokenService {
    String getUserId(String jwt);
    List<String> getUserRoles(String jwtToken);
}
