package com.rentalApp.auth.services;

import com.rentalApp.auth.dto.RoleDTO;

public interface RoleService {
    void createRole(RoleDTO roleDTO);
    RoleDTO findRoleById(String id);

}
