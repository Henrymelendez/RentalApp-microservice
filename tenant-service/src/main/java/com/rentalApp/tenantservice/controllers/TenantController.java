package com.rentalApp.tenantservice.controllers;


import com.rentalApp.tenantservice.dtos.TenantDTO;
import com.rentalApp.tenantservice.exceptions.UserNotAllowedException;
import com.rentalApp.tenantservice.services.TenantService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;


    @GetMapping("/all/{propertyId}")
    public List<TenantDTO> fetchTenants(@PathVariable String propertyId, HttpServletRequest httpServletRequest){
        return null;
    }
    @GetMapping("/fetch/{tenantId}")
    public TenantDTO fetchTenant(@PathVariable String tenantId, HttpServletRequest httpServletRequest) throws UserNotAllowedException {
        String propertyId = getJwtTokenFromHeader(httpServletRequest);
        return tenantService.fetchTenant(tenantId,propertyId);
    }
    private String getJwtTokenFromHeader(HttpServletRequest httpServletRequest){
        try {
            String tokenHeader = httpServletRequest.getHeader(AUTHORIZATION);
            return StringUtils.removeStart(tokenHeader, "Bearer ").trim();
        }catch (NullPointerException e){
            return StringUtils.EMPTY;
        }
    }

}
