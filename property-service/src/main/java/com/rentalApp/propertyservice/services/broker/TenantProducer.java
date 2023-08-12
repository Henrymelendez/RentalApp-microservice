package com.rentalApp.propertyservice.services.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TenantProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    private final static String TOPIC_NAME ="tenant.create";

    public void sendMessage(Object object){
        kafkaTemplate.send(TOPIC_NAME, object);
    }
}
