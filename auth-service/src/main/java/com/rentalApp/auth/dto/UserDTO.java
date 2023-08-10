package com.rentalApp.auth.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    private String username;
    private String displayName;
    private String password;
    private List<String> roles;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private String id;
    private String jwtToken;
}
