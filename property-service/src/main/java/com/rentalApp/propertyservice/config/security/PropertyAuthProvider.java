package com.rentalApp.propertyservice.config.security;

import com.rentalApp.propertyservice.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class PropertyAuthProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private TokenService tokenService;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        if(isEmpty(token)){
            return new User(username,"", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }
        else {
            List<String> roles = tokenService.getUserRoles(authentication.getCredentials().toString());
            return new User(username, "", AuthorityUtils.createAuthorityList(
                    roles.stream()
                            .map(role -> "ROLE_"+ role)
                            .toArray(String[]::new)
            ));
        }
    }
}
