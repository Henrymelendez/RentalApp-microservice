package com.rentalApp.auth.services;

import com.rentalApp.auth.dto.UserDTO;
import com.rentalApp.auth.dto.UserLoginDTO;

public interface UserService {

    void createUser(UserDTO userDTO);
    UserDTO findUserById(String id);
    UserDTO loginUser(UserLoginDTO userLoginDTO);
}
