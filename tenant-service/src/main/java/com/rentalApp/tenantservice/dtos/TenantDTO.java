package com.rentalApp.tenantservice.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class TenantDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String jwtToken;
    private String propertyId;
    private Date createdAt;
    private Date updatedAt;

}
