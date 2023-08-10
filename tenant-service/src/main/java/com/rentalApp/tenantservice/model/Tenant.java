package com.rentalApp.tenantservice.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@Document(collection = "tenants")
public class Tenant implements Serializable, Persistable<String> {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String propertyId;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    public Tenant(){
        this.id = UUID.randomUUID().toString();
    }
    @Override
    public boolean isNew() {
        return isNull(this.createdAt);
    }
}
