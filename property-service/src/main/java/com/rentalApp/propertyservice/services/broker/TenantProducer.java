package com.rentalApp.propertyservice.services.broker;

import com.rentalApp.propertyservice.dto.TenantDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantProducer {
    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(TenantDTO message){
        log.info("Payload Sent", message);
        kafkaTemplate.send(topicName,message);
    }


}
