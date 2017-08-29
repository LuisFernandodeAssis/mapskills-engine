/*
 * @(#)StudentTest.java 1.0 07/05/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import br.gov.sp.fatec.mapskills.authentication.PreAuthenticatedAuthentication;
import br.gov.sp.fatec.mapskills.authentication.jwt.JwtAuthenticationManager;
import br.gov.sp.fatec.mapskills.config.AbstractApplicationTest;
import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.answerevent.AnswerEvent;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
/**
 * 
 * A classe {@link StudentControllerIntegrationTest}
 *
 * @author Marcelo
 * @version 1.0 07/05/2017
 */
public class StudentControllerIntegrationTest extends AbstractApplicationTest {
	
	private static final String BASE_PATH = "/student";
	
	@Autowired
	private InstitutionService institutionSerivce;
	
	@Mock
	protected JwtAuthenticationManager jwtAuthenticationManager;
	
	@Before
	public void setuUp() {
		super.setUpContext();
		MockitoAnnotations.initMocks(this);
    	filter.setAuthenticationManager(jwtAuthenticationManager); 
	}
	
	@Test
	public void sendAnswerTest() throws Exception {
		mockStudentAuthentication();
		
		final AnswerEvent answer = AnswerEvent.builder().sceneIndex(0).sceneId(1L).studentId(1L).skillId(1L).skillValue(10).build();
		final String answerBody = objectMapper.writeValueAsString(answer);
		
		super.mockMvcPerformWithAuthorizationPost(BASE_PATH.concat("/game/answer"), answerBody)
			.andExpect(status().isCreated());
	}
	
	@Test
	public void getStudentDetails() throws Exception {
		this.mockStudentAuthentication();
		this.prepareStudentDataBase();
		
		final String studentReturn = super.mockMvcPerformWithAuthorizationGet(BASE_PATH.concat("/details/1460281423050"))
				.andReturn().getResponse().getContentAsString();
		
		assertTrue(studentReturn.contains("\"username\":\"aluno@fatec.sp.gov.br\""));
	}
	
	private void mockStudentAuthentication() throws MapSkillsException {
		when(jwtAuthenticationManager.authenticate(Mockito.any(Authentication.class)))
			.thenReturn(getStudentMock());
	}
	
	private Authentication getStudentMock() throws MapSkillsException {
		return new PreAuthenticatedAuthentication(super.getOneStudent());
	}
	
	private void prepareStudentDataBase() throws Exception {
		institutionSerivce.saveInstitution(getOneInstitution());
		institutionSerivce.saveStudent(getOneStudent());
	}

}
