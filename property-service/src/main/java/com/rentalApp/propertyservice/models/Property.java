package com.rentalApp.propertyservice.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@Document(collection = "properties")
public class Property implements Serializable, Persistable<String> {
    @Id
    private String id;
    @Indexed(unique = true)
    private String street;
    private String state;
    private String city;
    @Indexed(unique = true)
    private String userId;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    public Property(){
        this.id= UUID.randomUUID().toString();
    }
    @Override
    public boolean isNew() {
        return isNull(this.createdAt);
    }
}
