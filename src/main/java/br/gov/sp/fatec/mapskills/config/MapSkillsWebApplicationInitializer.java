/*
 * @(#)MapSkillsWebApplicationInitializer.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/**
 * 
 * A classe {@link MapSkillsWebApplicationInitializer}
 * representa a inicializacao do contexto do Spring na aplicacao.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
public class MapSkillsWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
	implements	WebApplicationInitializer {

	/** {@inheritDoc} */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SpringContextConfig.class};
	}

	/** {@inheritDoc} */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebConfig.class};
	}

	/** {@inheritDoc} */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/*"};
	}
	
	/** {@inheritDoc} */
	@Override
    protected Filter[] getServletFilters() {
        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setForceEncoding(true);
        encodingFilter.setEncoding("UTF-8");

        return new Filter[] {encodingFilter};
    }
	
	@Override
    public void onStartup(final ServletContext context) throws ServletException {
        super.onStartup(context);
        context.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class).addMappingForUrlPatterns(null, false, "/*");
    }

}
