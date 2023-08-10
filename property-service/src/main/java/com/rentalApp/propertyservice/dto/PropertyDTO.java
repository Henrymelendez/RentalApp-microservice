package com.rentalApp.propertyservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PropertyDTO {
    private String id;
    private String street;
    private String state;
    private String city;
    private String userId;
    private Date createdAt;
    private Date updatedAt;
}
