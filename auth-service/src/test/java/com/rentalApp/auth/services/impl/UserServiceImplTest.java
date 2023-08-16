package com.rentalApp.auth.services.impl;

import com.rentalApp.auth.dao.RoleDAO;
import com.rentalApp.auth.dao.UserDAO;
import com.rentalApp.auth.dto.UserDTO;
import com.rentalApp.auth.dto.UserLoginDTO;
import com.rentalApp.auth.model.User;
import com.rentalApp.auth.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private RoleDAO roleDAO;
    @Mock
    private TokenService tokenService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    @Container
    public static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("testpassword");

        User user = new User();
        user.setId("123");
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(roleDAO.findAll()).thenReturn(new ArrayList<>());
        when(bCryptPasswordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userDAO.save(user)).thenReturn(user);

        userService.createUser(userDTO);
        verify(userDAO).save(user);
        verify(modelMapper).map(user,userDTO);

    }

    @Test
    void findUserById() {
        String userId = "123";
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setId(userId);
        expectedUserDTO.setUsername("testuser");

        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(user,UserDTO.class)).thenReturn(expectedUserDTO);

        UserDTO results = userService.findUserById(userId);

        verify(userDAO).findById(userId);
        verify(modelMapper).map(user,UserDTO.class);

        assertEquals(expectedUserDTO,results);

    }

    @Test
    void loginUser() {
        String username = "testuser";
        String password = "testpassword";

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(username);
        userLoginDTO.setPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        when(userDAO.findByUsername(username)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password,user.getPassword())).thenReturn(true);
        when(modelMapper.map(user,UserDTO.class)).thenReturn(new UserDTO());
        userService.loginUser(userLoginDTO);

        verify(userDAO).findByUsername(username);
        verify(bCryptPasswordEncoder).matches(password,user.getPassword());
        verify(modelMapper).map(user,UserDTO.class);
    }

    @Test
    public void loginUserInvalidCredentials(){
        String username = "testuser";
        String password = "invalidPassword";

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(username);
        userLoginDTO.setPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        when(userDAO.findByUsername(username)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, user.getPassword()))
                .thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> userService.loginUser(userLoginDTO));
        verify(userDAO).findByUsername(username);
        verify(bCryptPasswordEncoder).matches(password, user.getPassword());
        verifyNoInteractions(modelMapper);
    }
}