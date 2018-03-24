/*
 * @(#)AuthenticationIntegrationTest.java 1.0 13/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.authentication;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.gov.sp.fatec.mapskills.MapSkillsApplication;
import br.gov.sp.fatec.mapskills.restapi.AbstractIntegrationTest;

/**
 * A classe {@link AuthenticationIntegrationTest} contem os teste de
 * autenticacao na aplicacao.
 *
 * @author Marcelo
 * @version 2.0 19/03/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MapSkillsApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationIntegrationTest extends AbstractIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Test
	public void login() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		final Cookie cookie = mvc.perform(post("/login")
			.param("username", "rafael.alves@fatec.sp.gov.br")
			.param("password", "mudar@123")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getCookie("Authorization");
		
		assertNotNull(cookie);
	}
	
	@Test
	public void loginUnauthorized() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		final String jsonExpected = getJsonAsString("json/expectations/login/unauthorized.json");
		final String message = mvc.perform(post("/login")
			.param("username", "rafael.alves.silva@fatec.sp.gov.br")
			.param("password", "mudar@123")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED))
			.andExpect(status().isUnauthorized())
			.andReturn()
			.getResponse()
			.getContentAsString();
		JSONAssert.assertEquals(jsonExpected, message, false);
	}
}