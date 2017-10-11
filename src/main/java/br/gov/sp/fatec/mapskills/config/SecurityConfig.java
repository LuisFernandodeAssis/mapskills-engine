/*
 * @(#)SecurityConfig.java 1.0 07/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nimbusds.jose.JOSEException;

import br.gov.sp.fatec.mapskills.authentication.AuthenticationListener;
import br.gov.sp.fatec.mapskills.authentication.PreAuthenticatedUserFilter;
import br.gov.sp.fatec.mapskills.authentication.jwt.JwtSignatureVerifier;
import br.gov.sp.fatec.mapskills.authentication.jwt.JwtVerifier;

/**
 * 
 * A classe {@link SecurityConfig} representa as configuracoes
 * de seguranca da aplicacao em nivel de URI.
 *
 * @author Marcelo
 * @version 1.0 07/01/2017
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    @Qualifier("jwtAuthenticationManager")
    private AuthenticationManager jwtAuthenticationManager;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    @Qualifier("providerManager")
    private AuthenticationManager providerManager;
	/**
	 * configuracao de seguranca em nivel de URI
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.addFilterBefore(corsFilter(), ChannelProcessingFilter.class).csrf().disable();
		http.addFilter(preAuthenticationFilter());
		http.addFilter(loginFilter());
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/images/**").permitAll();
		http.authorizeRequests().antMatchers("/user/details").authenticated();
		// For ADMIN or MENTOR
		http.authorizeRequests().antMatchers("/admin/institution/**").access("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_MENTOR'})");
		// For ADMIN only.
        http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMINISTRATOR')");
        //For MENTOR only.
        http.authorizeRequests().antMatchers("/institution/**").access("hasRole('ROLE_MENTOR')");
        //For STUDENT only.
        http.authorizeRequests().antMatchers("/student/**").access("hasRole('ROLE_STUDENT')");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	/**
	 * permite acesso as imagens do jogo com spring security,
	 * liberando essa rota para que não seje filtrada pelo spring security
	 */
	@Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**",
        		"/v2/api-docs", "/configuration/ui",
        		"/swagger-resources", "/configuration/security",
        		"/swagger-ui.html", "/webjars/**");
    }
	/**
	 * configuracao do Cross-Origin Resource Sharing (CORS) da aplicacao.
	 * @return
	 */
	@Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }
	/**
	 * filtro de pre-autenticacao que verifica a preseca
	 * do token no cabecalho.
	 * @return
	 */
	@Bean
	public Filter preAuthenticationFilter() {
		final PreAuthenticatedUserFilter filter = new PreAuthenticatedUserFilter();
		filter.setAuthenticationManager(jwtAuthenticationManager);
		return filter;
	}
	/**
	 * filtro que realiza o login do usuario na aplicacao
	 * passando pela rota padrao de login do spring security
	 * @return
	 */
	@Bean
	public Filter loginFilter() {
		final UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
		filter.setAuthenticationManager(providerManager);
		filter.setAuthenticationSuccessHandler(successHandler);
		filter.setAuthenticationFailureHandler(failureHandler);
		return filter;
	}
	
	@Bean
	public AuthenticationManager providerManager(@Qualifier("defaultAuthenticationProvider") final AuthenticationProvider provider) {
		return new ProviderManager(Arrays.asList(provider));
	}
	/**
	 * define um encriptador de senha para aplicacao
	 * @return
	 */
	@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(5);
    }
	
	@Bean
    public List<AuthenticationListener> authenticationListeners(
            @Qualifier("responseHeaderAuthenticationListener") final AuthenticationListener responseHeaderListener) {
        final List<AuthenticationListener> list = new ArrayList<>(1);
        list.add(responseHeaderListener);
        return list;
    }
	
	@Bean
    public List<JwtVerifier> verifiersList(@Qualifier("issuerReferenceClaimsVerifier") final JwtVerifier issuerVerifier,
            @Qualifier("notBeforeTimeClaimsVerifier") final JwtVerifier notBeforeTimeVerifier,
            @Qualifier("referenceDateClaimsVerifier") final JwtVerifier referenceDateVerifier,
            @Qualifier("jwtSignatureVerifier") final JwtVerifier jwtSignatureVerifier) {
        final List<JwtVerifier> verifiersList = new ArrayList<>(4);
        verifiersList.add(jwtSignatureVerifier);
        verifiersList.add(issuerVerifier);
        verifiersList.add(notBeforeTimeVerifier);
        verifiersList.add(referenceDateVerifier);
        return verifiersList;
    }
	
	@Bean
    public JwtVerifier jwtSignatureVerifier(@Value("${jwt.secret}") final String secret) throws JOSEException {
        return new JwtSignatureVerifier(secret);
    }
	
	@Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        final MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(new Object[] {SecurityContextHolder.MODE_INHERITABLETHREADLOCAL});
        return methodInvokingFactoryBean;
    }
	
}
