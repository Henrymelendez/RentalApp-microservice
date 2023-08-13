package com.rentalApp.tenantservice.services.impl;


import com.rentalApp.tenantservice.daos.TenantDAO;
import com.rentalApp.tenantservice.dtos.TenantDTO;
import com.rentalApp.tenantservice.model.Tenant;
import com.rentalApp.tenantservice.services.TenantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class TenantServiceImpl implements TenantService {
    @Autowired
    private TenantDAO tenantDAO;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createTenant(TenantDTO tenantDTO) {
        checkNotNull(tenantDTO);
        Tenant tenant = modelMapper.map(tenantDTO, Tenant.class);

        if(tenant.isNew()){
            tenantDAO.save(tenant);
        }
        else {
            Tenant existingTenant = tenantDAO.findById(tenant.getId()).orElse(null);
            if(existingTenant != null){
                existingTenant.setId(tenant.getId());
                existingTenant.setFirstName(tenant.getFirstName());
                existingTenant.setLastName(tenant.getFirstName());
                existingTenant.setPropertyId(tenant.getPropertyId());
                existingTenant.setCreatedAt(tenant.getCreatedAt());
                tenantDAO.save(existingTenant);
            }
        }

    }

    @Override
    public TenantDTO fetchTenant(String tenantId, String propertyId) {
        return null;
    }

    @Override
    public List<Tenant> fetchTenantsForPropertyId(String propertyId) {
        return null;
    }

    @Override
    public void updateTenant(TenantDTO tenantDTO, String propertyId) {

    }
    @Override
    public void deleteAll(String propertyId){
        checkNotNull(propertyId);
        tenantDAO.deleteAllByPropertyId(propertyId);
    }
}
