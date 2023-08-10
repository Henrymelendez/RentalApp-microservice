package com.rentalApp.propertyservice.config;

import com.rentalApp.propertyservice.config.security.PropertyAuthFilter;
import com.rentalApp.propertyservice.config.security.PropertyAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Autowired
    private PropertyAuthProvider propertyAuthProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(propertyAuthProvider)
                .addFilterBefore(propertyAuthFilter(), AnonymousAuthenticationFilter.class)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest()
                        .authenticated())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.disable())
                .cors(Customizer.withDefaults());
        return http.build();
    }
    private PropertyAuthFilter propertyAuthFilter() throws Exception {
        OrRequestMatcher orRequestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher("/property/**")
        );
        PropertyAuthFilter authFilter = new PropertyAuthFilter(orRequestMatcher);
        authFilter.setAuthenticationManager(authenticationManager());
        return authFilter;
    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
