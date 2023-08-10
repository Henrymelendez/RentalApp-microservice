package com.rentalApp.auth.dao;

import com.rentalApp.auth.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDAO extends MongoRepository<Role,String> {
    Optional<Role> findById(String s);
    Optional<Role> findByRole(String role);
}
