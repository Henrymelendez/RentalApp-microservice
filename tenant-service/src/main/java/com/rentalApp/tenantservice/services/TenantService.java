package com.rentalApp.tenantservice.services;

import com.rentalApp.tenantservice.dtos.TenantDTO;
import com.rentalApp.tenantservice.exceptions.UserNotAllowedException;
import com.rentalApp.tenantservice.model.Tenant;


import java.util.List;

public interface TenantService {

    void createTenant(TenantDTO tenantDTO);
    TenantDTO fetchTenant(String tenantId, String propertyId) throws UserNotAllowedException;
    List<Tenant> fetchTenantsForPropertyId(String propertyId);
    void deleteAll(String propertyId);
}
