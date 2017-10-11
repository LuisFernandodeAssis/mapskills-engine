/*
 * @(#)MongoConfig.java 1.0 1 19/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * A classe {@link MongoConfig} representa a configuracao do mongodb da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 19/09/2017
 */
@Configuration
@EnableMongoRepositories(basePackages = "org.baeldung.repository")
public class MongoConfig {
	
	@Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("localhost");
    }
 
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "mapskills");
    }

}