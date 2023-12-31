package com.rentalApp.auth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "roles")
@EqualsAndHashCode(callSuper = true)
public class Role extends GenericModel{
    private String role;

    public Role(){
        super();
    }
}
