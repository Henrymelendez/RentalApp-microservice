package com.rentalApp.auth.services.impl;

import com.rentalApp.auth.dao.RoleDAO;
import com.rentalApp.auth.dto.RoleDTO;
import com.rentalApp.auth.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest
class RoleServiceImplTest {
    @Container
    public static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Mock
    private RoleDAO roleRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRole() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole("Test Role");

        Role role = new Role();
        role.setId("123");
        role.setRole("Test Role");

        when(modelMapper.map(roleDTO, Role.class)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);

        roleService.createRole(roleDTO);
        verify(roleRepository).save(role);
    }

    @Test
    void findRoleById() {
        String roleId = "123";
        Role role = new Role();
        role.setId(roleId);
        role.setRole("Test Role");

        RoleDTO expectedRoleDTO = new RoleDTO();
        expectedRoleDTO.setId(roleId);
        expectedRoleDTO.setRole("Test Role");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(modelMapper.map(role, RoleDTO.class)).thenReturn(expectedRoleDTO);

        RoleDTO result = roleService.findRoleById(roleId);
        verify(roleRepository).findById(roleId);
        verify(modelMapper).map(role,RoleDTO.class);
    }
}