package com.rentalApp.auth.controllers;

import com.rentalApp.auth.dto.RoleDTO;
import com.rentalApp.auth.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/role")
@PreAuthorize("hasAnyRole('ADMIN')")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO){
        checkNotNull(roleDTO);
        roleService.createRole(roleDTO);
        return roleDTO;
    }
    @GetMapping("/info/{roleId}")
    public RoleDTO getRole(@PathVariable String roleId){
        return roleService.findRoleById(roleId);
    }
}
