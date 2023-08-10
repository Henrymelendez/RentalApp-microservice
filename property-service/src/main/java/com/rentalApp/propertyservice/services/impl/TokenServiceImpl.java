package com.rentalApp.propertyservice.services.impl;

import com.rentalApp.propertyservice.services.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String getUserId(String jwt) {
        if(isEmpty(jwt)){
            return StringUtils.EMPTY;
        }
        String claims = new String(Base64.getUrlDecoder().decode(jwt.split("\\.")[1]));
        JSONObject claimJson = new JSONObject(claims);
        return claimJson.getString("iss");
    }

    @Override
    public List<String> getUserRoles(String jwtToken) {
        String claims = new String(Base64.getUrlDecoder().decode(jwtToken.split("\\.")[1]));
        JSONObject claimJson = new JSONObject(claims);
        String audience = claimJson.getString("aud");
        final String[] split = audience.replace("[","")
                .replace("]","")
                .split(",");
        return Stream.of(split).map(String::trim).collect(Collectors.toList());
    }
}
