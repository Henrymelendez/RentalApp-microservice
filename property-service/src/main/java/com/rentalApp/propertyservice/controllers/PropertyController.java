package com.rentalApp.propertyservice.controllers;

import com.rentalApp.propertyservice.dto.PropertyDTO;
import com.rentalApp.propertyservice.dao.exceptions.UserNotAllowedException;
import com.rentalApp.propertyservice.services.PropertyService;
import com.rentalApp.propertyservice.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/property")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private TokenService tokenService;


    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER','ADMIN'")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDTO createProperty(@RequestBody PropertyDTO propertyDTO, HttpServletRequest httpServletRequest){
        checkNotNull(propertyDTO);
        String jwtToken = getJwtTokenFromHeader(httpServletRequest);
        String userId= tokenService.getUserId(jwtToken);
        propertyDTO.setUserId(userId);
        propertyService.createProperty(propertyDTO);
        return propertyDTO;
    }
    @GetMapping("/info/{propertyId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN'")
    public PropertyDTO getProperty(@PathVariable String propertyId, HttpServletRequest httpServletRequest) throws UserNotAllowedException {
        String jwtToken = getJwtTokenFromHeader(httpServletRequest);
        String userId = tokenService.getUserId(jwtToken);
        return propertyService.fetchProperty(propertyId,userId);
    }

    @GetMapping("/all/{userId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN'")
    public List<PropertyDTO> getAllPropertiesForUser(@PathVariable String userId, HttpServletRequest httpServletRequest) throws UserNotAllowedException {
        String jwtToken = getJwtTokenFromHeader(httpServletRequest);
        String callerUserId = tokenService.getUserId(jwtToken);
        return propertyService.fetchPropertyForUserId(userId,callerUserId);
    }
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER','ADMIN'")
    public PropertyDTO updateProperty(@RequestBody PropertyDTO property, HttpServletRequest httpServletRequest){
        String jwtToken = getJwtTokenFromHeader(httpServletRequest);
        String userId = tokenService.getUserId(jwtToken);
        propertyService.updateProperty(property,userId);
        return property;
    }
    private String getJwtTokenFromHeader(HttpServletRequest httpServletRequest){
        try {
            String tokenHeader = httpServletRequest.getHeader(AUTHORIZATION);
            return StringUtils.removeStart(tokenHeader,"Bearer ").trim();
        }catch (NullPointerException e){
            return StringUtils.EMPTY;
        }
    }

}
