/*
 * @(#)WebConfig.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
/**
 * 
 * A classe {@link WebConfig} contem as configuracoes
 * que diz respeito a toda parte de servico web.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Configuration
@EnableWebMvc
@ComponentScan("br.gov.sp.fatec.mapskills.restapi")
@Import({SwaggerConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Resource
    private Environment env;

	@Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
	
	@Bean
    public ContentNegotiatingViewResolver contentViewResolver() {
        final ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);
        final MappingJackson2JsonView defaultJsonView = new MappingJackson2JsonView();
        defaultJsonView.setExtractValueFromSingleKeyModel(true);
        final ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
        contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
        return contentViewResolver;
    }	
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    /**
     * configuracao que permite acesso a pasta images da aplicacao sem restricao,
     * com spring security configurado 
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(31556926);

        /*
         * SWAGGER UI CONFIGURATION
         */
        registry.addResourceHandler("swagger-ui.html")
        		.addResourceLocations("classpath:/META-INF/resources/");
       
        registry.addResourceHandler("/webjars/**")
        		.addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
