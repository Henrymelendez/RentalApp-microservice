package com.rentalApp.auth.intialize;

import com.rentalApp.auth.dao.RoleDAO;
import com.rentalApp.auth.dao.UserDAO;
import com.rentalApp.auth.model.Role;
import com.rentalApp.auth.model.User;
import com.rentalApp.auth.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile({"dev","test"})
public class IntializeTestData {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    TokenService tokenService;


    @EventListener
    public void appReady(ApplicationReadyEvent event){
        addRoles();
        addUsers();
    }


    private void addRoles(){
        userDAO.deleteAll();
        roleDAO.deleteAll();

        Role role = new Role();
        role.setRole("ADMIN");

        Role role1 = new Role();
        role1.setRole("USER");

        roleDAO.save(role);
        roleDAO.save(role1);
    }

    private void addUsers(){
        userDAO.deleteAll();

        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@admin.com");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        user.setRoles(Collections.singletonList("ADMIN"));
        tokenService.generateToken(user);
        user.setDisplayName("adminDisplayName");

        userDAO.save(user);


        User user1 = new User();
        user1.setUsername("user");
        user1.setEmail("user@user.com");
        user1.setPassword(bCryptPasswordEncoder.encode("user"));
        user1.setRoles(Collections.singletonList("USER"));
        tokenService.generateToken(user1);
        user1.setDisplayName("userDisplayName");

        userDAO.save(user1);

    }
}
