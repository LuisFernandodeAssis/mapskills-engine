/*
 * @(#)SpringContextConfigurationTest.java 1.0 13/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.sp.fatec.mapskills.infra.ThreadPool;
/**
 * 
 * A classe {@link SpringContextTestConfiguration} representa as configuracoes
 * necessarias para o realizacao dos testes da aplicacao, utilizando
 * banco de dados em memoria no caso o HSQLDB.
 *
 * @author Marcelo
 * @version 1.0 13/11/2016
 */
@Import({DatabaseTestConfig.class})
@ComponentScan(basePackages = {"br.gov.sp.fatec.mapskills.test.serializer"})
public class SpringContextTestConfiguration extends AbstractSpringContextConfiguration {
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public ThreadPool threadPool() {
		return new ThreadPool();
	}
	
}
