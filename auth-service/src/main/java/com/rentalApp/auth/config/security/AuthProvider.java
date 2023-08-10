package com.rentalApp.auth.config.security;

import com.rentalApp.auth.dao.UserDAO;
import com.rentalApp.auth.exceptions.InvalidTokenException;
import com.rentalApp.auth.exceptions.UserTokenAuthException;
import com.rentalApp.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        final String token = authentication.getCredentials().toString();

        if(isEmpty(token)){
            return new User(username, "", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }

        Optional<com.rentalApp.auth.model.User> userOptional = userDAO.findByJwtToken(token);

        if(userOptional.isPresent()){
            final com.rentalApp.auth.model.User user = userOptional.get();

            try {
                tokenService.validateToken(token);
            } catch (InvalidTokenException e) {
                user.setJwtToken(null);
                userDAO.save(user);
                return null;
            }

            return new User(username, "", AuthorityUtils.createAuthorityList(
                    user.getRoles().stream()
                            .map(roleName -> "ROLE_" + roleName)
                            .toArray(String[]::new)
            ));

        }
        throw new UserTokenAuthException("User Not Found For Token: "+token);

    }
}
