package com.rentalApp.auth.dao;

import com.rentalApp.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends MongoRepository<User,String> {

    Optional<User> findByJwtToken(String jwtToken);
    Optional<User> findByUsername(String username);
    List<User> findByUsernameOrDisplayNameOrEmail(String s);
}
