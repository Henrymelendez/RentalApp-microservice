package com.rentalApp.auth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class User extends GenericModel{
    @Indexed(direction = IndexDirection.DESCENDING,unique = true)
    private String username;
    @Indexed(direction = IndexDirection.DESCENDING, unique = true)
    private String displayName;
    private String jwtToken;
    private String password;
    @Indexed(direction = IndexDirection.DESCENDING, unique = true)
    private String email;
    private List<String> roles;

    public User(){
        super();
    }
}
