package com.rentalApp.tenantservice.services.broker;

import com.rentalApp.tenantservice.dtos.TenantDTO;
import com.rentalApp.tenantservice.services.TenantListenerService;
import com.rentalApp.tenantservice.services.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TenantListenerServiceImpl implements TenantListenerService {
    @Autowired
    private TenantService tenantService;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${topic.name.consumer}")
    private String topicName;




    @Override
    @KafkaListener(topics = "${topic.name.consumer}", groupId = "tenant")
    public void consumeTenant(TenantDTO payload) {
        TenantDTO tenantDTO = payload;
        tenantService.createTenant(tenantDTO);
        System.out.println("Got it");
    }


}
