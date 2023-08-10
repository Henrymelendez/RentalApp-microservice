package com.rentalApp.auth.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RoleDTO {
    private String role;
    private String id;
    private Date createdAt;
    private Date updatedAt;
}
