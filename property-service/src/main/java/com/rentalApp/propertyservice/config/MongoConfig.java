package com.rentalApp.propertyservice.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String host;
    @Value("${soring.data.mongodb.database}")
    private String database;

    @Override
    protected String getDatabaseName() {
        return this.database;
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(this.host);
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
