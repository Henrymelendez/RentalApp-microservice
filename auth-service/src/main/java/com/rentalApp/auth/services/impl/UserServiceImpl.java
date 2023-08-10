package com.rentalApp.auth.services.impl;

import com.rentalApp.auth.dao.RoleDAO;
import com.rentalApp.auth.dao.UserDAO;
import com.rentalApp.auth.dto.UserDTO;
import com.rentalApp.auth.dto.UserLoginDTO;
import com.rentalApp.auth.model.Role;
import com.rentalApp.auth.model.User;
import com.rentalApp.auth.services.TokenService;
import com.rentalApp.auth.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserServiceImpl implements UserService {
    private static final String UNKNOWN_USERNAME_OR_BAD_PASSWORD= "Unknown Username or Bad Password";
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private TokenService tokenService;
    @Override
    public void createUser(UserDTO userDTO) {
        checkNotNull(userDTO.getPassword());
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRoles(
                roleDAO.findAll()
                        .stream()
                        .map(Role::getRole)
                        .filter(role -> role.contains("USER"))
                        .collect(Collectors.toList())
        );
        tokenService.generateToken(user);
        userDAO.save(user);
        userDTO.setPassword("");
        modelMapper.map(user,userDTO);
    }
    @Override
    public UserDTO findUserById(String id) {
        Optional<User> userOptional = userDAO.findById(id);
        if(userOptional.isPresent()){
            return modelMapper.map(userOptional.get(),UserDTO.class);
        }
        return null;
    }
    @Override
    public UserDTO loginUser(UserLoginDTO userLoginDTO) {
        Optional<User> userOptional = userDAO.findByUsername(userLoginDTO.getUsername());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())){
                return modelMapper.map(user,UserDTO.class);
            }
            else{
                throw new BadCredentialsException(UNKNOWN_USERNAME_OR_BAD_PASSWORD);
            }
        }
        else {
            throw new BadCredentialsException(UNKNOWN_USERNAME_OR_BAD_PASSWORD);
        }
    }
}
