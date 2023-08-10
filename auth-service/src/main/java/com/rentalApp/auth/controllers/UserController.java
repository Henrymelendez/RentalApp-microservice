package com.rentalApp.auth.controllers;

import com.rentalApp.auth.dto.UserDTO;
import com.rentalApp.auth.dto.UserLoginDTO;
import com.rentalApp.auth.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("create")
    @PreAuthorize("hasAnyRole('ANONYMOUS','ADMIN')")
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        checkNotNull(userDTO);
        userService.createUser(userDTO);
        return  userDTO;

    }
    @GetMapping("/info/{userId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserDTO getUser(@PathVariable String userId){
        return userService.findUserById(userId);
    }
    @PostMapping("/login")
    @PreAuthorize("hasAnyRole('ANONYMOUS')")
    public UserDTO loginUser(@RequestBody UserLoginDTO userLoginDTO){
        checkNotNull(userLoginDTO);
        return userService.loginUser(userLoginDTO);
    }
}
