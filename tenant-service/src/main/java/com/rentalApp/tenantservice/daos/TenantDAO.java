package com.rentalApp.tenantservice.daos;

import com.rentalApp.tenantservice.model.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantDAO extends MongoRepository<Tenant,String> {
    List<Tenant> findAllByPropertyIdOrderByUpdatedAtDesc(String propertyId);
    void deleteAllByPropertyId(String propertyId);
}
