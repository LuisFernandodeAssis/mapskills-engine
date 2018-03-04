/*
 * @(#)SpringContextTestConfiguration.java 1.0 1 24/02/2018
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import br.gov.sp.fatec.mapskills.utils.ThreadPool;

/**
 * A classe {@link SpringContextTestConfig}
 *
 * @author Marcelo
 * @version 1.0 24/02/2018
 */
@Configuration
@Profile("test")
public class SpringContextTestConfig {
	
	@Bean("threadPool")
    public ThreadPool threadPool() {
        return Mockito.mock(ThreadPool.class);
    }
	
	@Bean
    public RestTemplate restTemplate() {
        return Mockito.mock(RestTemplate.class);
    }
}