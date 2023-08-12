package com.rentalApp.propertyservice.controllers;


import com.rentalApp.propertyservice.dto.TenantDTO;
import com.rentalApp.propertyservice.services.broker.TenantProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/broker")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class TenantController {

    @Autowired
    private TenantProducer tenantProducer;


    @PostMapping("/create")
    public String publishTenant(@RequestBody TenantDTO tenantDTO){
        tenantProducer.sendMessage(tenantDTO);
        System.out.println("Success from producer");
        return "Published " + tenantDTO;
    }


}
