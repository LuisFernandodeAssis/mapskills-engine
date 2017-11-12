/*
 * @(#)InstitutionTest.java 1.0 15/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.sp.fatec.mapskills.application.InstitutionApplicationServices;
import br.gov.sp.fatec.mapskills.application.StudentApplicationServices;
import br.gov.sp.fatec.mapskills.authentication.PreAuthenticatedAuthentication;
import br.gov.sp.fatec.mapskills.authentication.jwt.JwtAuthenticationManager;
import br.gov.sp.fatec.mapskills.config.AbstractApplicationTest;
import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
import br.gov.sp.fatec.mapskills.domain.institution.Period;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.CourseWrapper;
/**
 * 
 * A classe {@link InstitutionControllerIntegrationTest}
 *
 * @author Marcelo
 * @version 1.0 15/01/2017
 */
public class InstitutionControllerIntegrationTest extends AbstractApplicationTest {
	
	private static final String BASE_PATH = "/institution";
	
	@Mock
	private JwtAuthenticationManager jwtAuthenticationManager;
	
	@Autowired
	private InstitutionApplicationServices services;
	
	@Autowired
	private StudentApplicationServices studentServices;
	
	@Autowired
	private ObjectMapper objectMapper;
	/**
	 * autentica um usuario mentor para que passe pelo
	 * spring security
	 */
	@Before
	public void setUp() {
		super.setUpContext();
		MockitoAnnotations.initMocks(this);
    	filter.setAuthenticationManager(jwtAuthenticationManager);
	}
	
	@Test
	public void uploadStudentsFromExcel() throws Exception {
		mockMentorAuthentication();
		saveCoursesMock();

		final String json = super.parseFileToJson("student.xlsx");
		
		super.mockMvcPerformWithAuthorizationPost(BASE_PATH.concat("/upload/students"), json)
			.andExpect(status().isCreated());
		
		//assertEquals(2, services.findAllStudentsByInstitution("146").size());
	}
	
	@Test
	public void saveCourse() throws Exception {
		mockMentorAuthentication();
		
		final Course course = new Course("100", "manutenção de aeronaves", Period.NIGHTLY);
		final String json = objectMapper.writeValueAsString(new CourseWrapper(course));
		
		super.mockMvcPerformWithAuthorizationPost(BASE_PATH.concat("/course"), json)
			.andExpect(status().isCreated());
		
		//assertEquals(1, services.findInstitutionByCode("146").getCourses().size());
	}
	
	@Test
	public void findAllStudentsByInstitutionCode() throws Exception {
		mockMentorAuthentication();
		
		services.saveInstitution(getOneInstitution());
		
		final MvcResult result = super.mockMvcPerformWithAuthorizationGet(BASE_PATH.concat("/146/students")).andReturn();
		
		final String jsonResponse = result.getResponse().getContentAsString();
		final Object[] allStudentsAsArray = objectMapper.readValue(jsonResponse, Object[].class);
		final Collection<Object> allStudents = new ArrayList<>(4);
		allStudents.addAll(Arrays.asList(allStudentsAsArray));
		
		assertEquals(4, allStudents.size());
	}
	
	@Test
	public void findAllCoursesByInstitution() throws Exception {
		mockMentorAuthentication();

		final Institution fatec = getOneInstitution();

		fatec.addCourse(new Course("100", "manutenção de aeronaves", Period.NIGHTLY));
		
		getCoursesMock(fatec).forEach(course -> {
			fatec.addCourse(course);
		});

		services.saveInstitution(fatec);
		
		final MvcResult result = super.mockMvcPerformWithAuthorizationGet(BASE_PATH.concat("/146/courses")).andReturn();
		
		final String jsonResponse = result.getResponse().getContentAsString();
		final Course[] allCourseAsArray = objectMapper.readValue(jsonResponse, Course[].class);
		final List<Course> allCourses = Arrays.asList(allCourseAsArray);
		
		assertEquals(4, allCourses.size());
	}
	
	@Test
	public void getStudentsProgress() throws Exception {
		mockMentorAuthentication();
		
		final Institution institution = getOneInstitution();
		
		institution.addCourse(new Course("028", "manutenção de aeronaves", Period.NIGHTLY));
		//studentServices.saveStudents(getStudentsMock());
		//TODO criar objetos para as VIEWS do banco de dados.
		//final MvcResult result = super.mockMvcPerformWithMockHeaderGet(BASE_PATH.concat("/146/progress")).andReturn();
	}
	
	@Test
	public void expectedExceptionTest() throws Exception {
		mockMentorAuthentication();
		
		//final Student studentA = getOneStudent();
		//final String studentAJson = objectMapper.writeValueAsString(new StudentWrapperTest(studentA));
		//super.mockMvcPerformWithAuthorizationPost(BASE_PATH.concat("/student"), studentAJson).andExpect(status().isCreated());
		//super.mockMvcPerformWithAuthorizationPost(BASE_PATH.concat("/student"), studentAJson).andExpect(status().isBadRequest());
	}
	
	private void mockMentorAuthentication() {
		when(jwtAuthenticationManager.authenticate(Mockito.any(Authentication.class)))
			.thenReturn(getMentorMock());
	}
	
	private Authentication getMentorMock() {
		return new PreAuthenticatedAuthentication(new Mentor("mentor", "admin@cps.sp.gov.br", "admin"));
	}
	
	private List<Course> getCoursesMock(final Institution institution) {
		final List<Course> courses = new ArrayList<>(3);
		courses.add(new Course("100", "manutenção de aeronaves", Period.NIGHTLY));
		courses.add(new Course("200", "logistica", Period.NIGHTLY));
		courses.add(new Course("300", "analise de sistemas", Period.NIGHTLY));
		return courses;
	}
	
	private void saveCoursesMock() {
		final Institution institution = getOneInstitutionWithoutCourses();
		institution.addCourse(new Course("030", "manutenção de aeronaves", Period.NIGHTLY));
		institution.addCourse(new Course("031", "manutenção de aeronaves", Period.NIGHTLY));
		services.saveInstitution(institution);
	}
	
	private Institution getOneInstitutionWithoutCourses() {
		return new Institution("146", null, null, null, null, Collections.emptyList(), Collections.emptyList(), null);
	}
}