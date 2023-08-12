package com.rentalApp.tenantservice.services;

import com.rentalApp.tenantservice.dtos.TenantDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface TenantListenerService {

    void consumeTenant(TenantDTO payload);
  //  void consumeDeleteAllTenants(ConsumerRecord<String,String> payload);


}
