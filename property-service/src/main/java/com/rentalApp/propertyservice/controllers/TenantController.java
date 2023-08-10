package com.rentalApp.propertyservice.controllers;

import com.rentalApp.propertyservice.dto.TenantDTO;
import com.rentalApp.propertyservice.services.broker.TenantProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/broker")
public class TenantController {

    private final TenantProducer tenantProducer;

    @PostMapping(value = "/create")
    public String sendTenant(@RequestBody TenantDTO tenantDTO){
        tenantProducer.send(tenantDTO);
        return "Tenant Sent";
    }

}
