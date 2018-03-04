/*
 * @(#)AbstractApplicationTest.java 1.0 13/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.servlet.Filter;

import org.apache.commons.io.IOUtils;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * A classe {@link AbstractApplicationTest} representa as configuracoes
 * globais de teste, para todos outros testes.
 *
 * @author Marcelo
 * @version 1.0 13/01/2017
 */
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class})
public abstract class AbstractApplicationTest {
	
	protected static final String AUTHORIZATION = "Authorization";
	protected static final String JSON_UTF8_MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
		
	@Autowired
	protected AbstractPreAuthenticatedProcessingFilter filter;
	
	@Autowired
    protected WebApplicationContext wac;
	
	@Autowired
    private Filter springSecurityFilterChain;
	
	@Autowired
	protected PasswordEncoder encoder;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	protected MockMvc mockMvc;
	
	/**
	 * configuração inicial para mockar os teste de integração
	 */
    protected void setUpContext() {
		mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .addFilters(springSecurityFilterChain)
                .build();
    }	
	
	protected ResultActions mockMvcPerformGet(final String request) throws Exception {
		return mockMvc.perform(get(request)
				.accept(MediaType.parseMediaType(JSON_UTF8_MEDIA_TYPE)));
	}
	
	protected ResultActions mockMvcPerformWithAuthorizationPost(final String request, final String body) throws Exception {
		return mockMvc.perform(post(request)
				.header(AUTHORIZATION, Mockito.anyString())
				.content(body)
				.contentType(JSON_UTF8_MEDIA_TYPE));
	}
	
	protected ResultActions mockMvcPerformWithAuthorizationGet(final String request) throws Exception {
		return mockMvc.perform(get(request)
				.header(AUTHORIZATION, Mockito.anyString())
				.accept(MediaType.parseMediaType(JSON_UTF8_MEDIA_TYPE)));
	}
	
	protected ResultActions mockMvcPerformWithAuthorizationPut(final String request, final String body) throws Exception {
		return mockMvc.perform(put(request)
				.header(AUTHORIZATION, Mockito.anyString())
				.content(body)
				.contentType(MediaType.parseMediaType(JSON_UTF8_MEDIA_TYPE)));
	}
	
	protected ResultActions mockMvcPerformLogin(final String username, final String password) throws Exception {
		return mockMvc.perform(post("/login")
				.param("username", username)
				.param("password", password)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED));
	}
	
	protected String parseFileToJson(final String fileName) throws IOException {
		final InputStream inputStream = getClass().getClassLoader().getResource(fileName).openStream();
		final String excelBase64 = Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream));
		
		final String obj = objectMapper.writeValueAsString(String.format("{ base64 : %s }", excelBase64));
		final String json = obj.replace(" ", "\"").substring(1, obj.length()-1);
		return json;
	}
}