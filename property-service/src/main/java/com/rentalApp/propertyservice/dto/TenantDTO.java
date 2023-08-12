package com.rentalApp.propertyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TenantDTO {
    private String jwtToken;
    private String propertyId;
    private String firstName;
    private String lastName;
}
