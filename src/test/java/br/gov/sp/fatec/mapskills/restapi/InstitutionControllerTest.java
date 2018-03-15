/*
 * @(#)InstitutionControllerTest.java 1.0 1 11/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.gov.sp.fatec.mapskills.MapSkillsApplication;

/**
 * A classe {@link InstitutionControllerTest}
 *
 * @author Marcelo
 * @version 1.0 11/11/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MapSkillsApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InstitutionControllerTest extends AbstractIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Test
	@WithMockUser
	public void getAllInstitution() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		final String jsonExpected = getJsonAsString("json/expectations/institution/institutions.json");
		final MvcResult result = performGet(mvc, "/institutions").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void importInstitutions() throws Exception {
		final String importJson = getJsonAsString("json/request/import-base64.json");
		final String base64 = getFileAsBase64("import-institutions.xlsx");
		performPost(mvc, "/institution/upload", String.format(importJson, base64)).andExpect(status().isOk());
		verifyDatasetForTable("institution/imported-institutions.xml", "LOGIN", "SELECT * FROM MAPSKILLS.LOGIN ORDER BY USERNAME", new String[]{"ID", "PASSWORD"});
		verifyDatasetForTable("institution/imported-institutions.xml", "INSTITUTION", "SELECT * FROM MAPSKILLS.INSTITUTION ORDER BY CODE", new String[]{"ID"});
		verifyDatasetForTable("institution/imported-institutions.xml", "MENTOR", "SELECT * FROM MAPSKILLS.MENTOR ORDER BY NAME", new String[]{"ID", "ID_LOGIN", "ID_INSTITUTION"});
	}
	
	@Test
	@WithMockUser
	public void saveInstitution() throws Exception {
		final String institutionJson = getJsonAsString("json/request/institution/new-institution.json");
		performPost(mvc, "/institution", institutionJson).andExpect(status().isOk());
		verifyDatasetForTable("institution/created-institution.xml", "LOGIN", "SELECT * FROM MAPSKILLS.LOGIN", new String[]{"ID", "PASSWORD"});
		verifyDatasetForTable("institution/created-institution.xml", "INSTITUTION", "SELECT * FROM MAPSKILLS.INSTITUTION", new String[]{"ID"});
		verifyDatasetForTable("institution/created-institution.xml", "MENTOR", "SELECT * FROM MAPSKILLS.MENTOR", new String[]{"ID", "ID_LOGIN", "ID_INSTITUTION"});
	}
	
	@Test
	@WithMockUser
	public void saveInstitutionWith2Mentors() throws Exception {
		final String institutionJson = getJsonAsString("json/request/institution/new-institution-two-mentors.json");
		performPost(mvc, "/institution", institutionJson).andExpect(status().isOk());
		verifyDatasetForTable("institution/created-institution-two-mentors.xml", "LOGIN", "SELECT * FROM MAPSKILLS.LOGIN ORDER BY USERNAME", new String[]{"ID", "PASSWORD"});
		verifyDatasetForTable("institution/created-institution-two-mentors.xml", "INSTITUTION", "SELECT * FROM MAPSKILLS.INSTITUTION", new String[]{"ID"});
		verifyDatasetForTable("institution/created-institution-two-mentors.xml", "MENTOR", "SELECT * FROM MAPSKILLS.MENTOR ORDER BY NAME", new String[]{"ID", "ID_LOGIN", "ID_INSTITUTION"});
	}
	
	@Test
	@WithMockUser
	public void updateInstitution() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		final String institutionJson = getJsonAsString("json/request/institution/institution-update.json");
		performPut(mvc, "/institution/1", institutionJson).andExpect(status().isOk());
		verifyDatasetForTable("institution/updated-institution.xml", "LOGIN", "SELECT * FROM MAPSKILLS.LOGIN WHERE ID = 1", new String[]{"PASSWORD"});
		verifyDatasetForTable("institution/updated-institution.xml", "INSTITUTION", "SELECT * FROM MAPSKILLS.INSTITUTION WHERE ID = 1", new String[]{});
		verifyDatasetForTable("institution/updated-institution.xml", "MENTOR", "SELECT * FROM MAPSKILLS.MENTOR WHERE ID = 1", new String[]{});
	}
	
	@Test
	@WithMockUser
	public void getInstitution() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-course.sql");
		final String jsonExpected = getJsonAsString("json/expectations/institution/institution.json");
		final MvcResult result = performGet(mvc, "/institution/1").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser(username = "isabel.cardoso@etec.sp.gov.br")
	public void saveCourse() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		final String courseJson = getJsonAsString("json/request/institution/new-course.json");
		performPost(mvc, "/institution/course", courseJson).andExpect(status().isOk());
		verifyDatasetForTable("institution/created-course.xml", "COURSE", "SELECT * FROM MAPSKILLS.COURSE", new String[]{"ID"});
	}
	
	@Test
	@WithMockUser(username = "rafael.alves@fatec.sp.gov.br")
	public void updateCourse() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-course.sql");
		final String courseJson = getJsonAsString("json/request/institution/course-update.json");
		performPut(mvc, "/institution/course/1", courseJson).andExpect(status().isOk());
		verifyDatasetForTable("institution/updated-course.xml", "COURSE", "SELECT * FROM MAPSKILLS.COURSE", new String[]{});
	}
	
	@Test
	@WithMockUser
	public void getFatecStudents() throws Exception {
		preparedStudents();
		final String jsonExpected = getJsonAsString("json/expectations/institution/students-fatec.json");
		final MvcResult result = performGet(mvc, "/institution/students?" + getParams("", "", "146", "", 0, 20))
				.andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void getEetcStudents() throws Exception {
		preparedStudents();
		final String jsonExpected = getJsonAsString("json/expectations/institution/students-etec.json");
		final MvcResult result = performGet(mvc, "/institution/students?" + getParams("", "", "282", "", 0, 20))
				.andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	private String getParams(final String name, final String ra, final String institutionCode,
			final String courseCode, final int page, final int size) {
		
		final String params = "name=%s&ra=%s&institutionCode=%s&courseCode=%s&page=%d&size=%d";		
		return String.format(params, name, ra, institutionCode, courseCode, page, size).replace("\"", "");
	}
	
	private void preparedStudents() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-course.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-students.sql");
	}
}