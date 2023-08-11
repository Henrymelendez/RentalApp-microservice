package com.rentalApp.tenantservice;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
public class TenantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenantServiceApplication.class, args);
	}

	@Bean
	public NewTopic topic(){
		return TopicBuilder.name("tenant.create")
				.partitions(1)
				.replicas(1)
				.build();
	}

}
