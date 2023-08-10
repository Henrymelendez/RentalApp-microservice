package com.rentalApp.propertyservice.dto;

import lombok.Data;

@Data
public class TenantDTO {
    private String jwtToken;
    private String propertyId;
    private String firstName;
    private String lastName;
}
