/*
 * @(#)SpringContextConfiguration.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.sp.fatec.mapskills.infra.ThreadPool;

/**
 * A classe {@link SpringContextConfig} representa as
 * configuracoes necessarias para o uso da aplicacao em producao.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Configuration
@PropertySource({"classpath:application.properties", "classpath:authentication.properties"})
public class SpringContextConfig {
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ThreadPool threadPool() {
		return new ThreadPool();
	}	
}