/*
 * @(#)SwaggerConfig.java 1.0 06/05/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * A classe {@link SwaggerConfig} representa as
 * configuracoes basicas do <i>Swagger</i> para
 * a documentacao das api's utilizadas pela aplicacao.
 * Importacao realizada na classe {@link WebConfig}.
 *
 * @author Marcelo
 * @version 1.0 06/05/2017
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("br.gov.sp.fatec.mapskills.restapi"))              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
}