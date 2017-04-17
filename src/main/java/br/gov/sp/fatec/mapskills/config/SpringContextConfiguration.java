/*
 * @(#)SpringContextConfiguration.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * A classe <code>SpringContextConfiguration</code> representa as configura��es
 * necess�rias para o uso da aplica��o em produ��o.
 * 
 * @author Marcelo
 *
 */
@Configuration
@Import({DataBaseConfig.class})
public class SpringContextConfiguration extends AbstractSpringContextConfiguration {
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
}
