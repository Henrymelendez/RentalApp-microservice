package com.rentalApp.auth.services.impl;

import com.rentalApp.auth.dao.RoleDAO;
import com.rentalApp.auth.dto.RoleDTO;
import com.rentalApp.auth.model.Role;
import com.rentalApp.auth.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createRole(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        roleDAO.save(role);
        modelMapper.map(role,roleDTO);
    }

    @Override
    public RoleDTO findRoleById(String id) {
        Optional<Role> role = roleDAO.findById(id);
        if(role.isPresent()){
            final Role role1 = role.get();
            return modelMapper.map(role1, RoleDTO.class);
        }
        return null;
    }
}
