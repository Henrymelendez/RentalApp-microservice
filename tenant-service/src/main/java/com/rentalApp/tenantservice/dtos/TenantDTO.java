package com.rentalApp.tenantservice.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class TenantDTO {
    private String jwtToken;
    private String propertyId;
    private String firstName;
    private String lastName;

}
