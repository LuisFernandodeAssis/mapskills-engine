/*
 * @(#)StudentControllerTest.java 1.0 1 11/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
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
import br.gov.sp.fatec.mapskills.utils.ThreadPool;

/**
 * A classe {@link StudentControllerTest} contem os testes de integracao
 * para o dominio de <code>Student</code>.
 *
 * @author Marcelo
 * @version 1.0 11/11/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MapSkillsApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentControllerTest extends AbstractIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ThreadPool threadPool;
	
	@Before
	public void before() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-course.sql");
	}
	
	@Test
	@WithMockUser
	public void importStudents() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student.sql");
		final String importJson = getJsonAsString("json/request/import-base64.json");
		final String base64 = getFileAsBase64("import-students.xlsx");
		performPost(mvc, "/students", String.format(importJson, base64)).andExpect(status().isOk());
		verifyDatasetForTable("student/imported-students.xml", "LOGIN", "SELECT * FROM MAPSKILLS.LOGIN WHERE ID > 2 ORDER BY USERNAME", new String[]{"ID", "PASSWORD"});
		verifyDatasetForTable("student/imported-students.xml", "STUDENT", "SELECT * FROM MAPSKILLS.STUDENT ORDER BY RA", new String[]{"ID", "ID_LOGIN"});
	}
	
	@Test
	@WithMockUser
	public void importStudentsWithIncompleteFile() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student.sql");
		final String importJson = getJsonAsString("json/request/import-base64.json");
		final String base64 = getFileAsBase64("import-students-incomplete.xlsx");
		performPost(mvc, "/students", String.format(importJson, base64)).andExpect(status().isOk());
		verifyDatasetForTable("student/imported-students-incomplete-file.xml", "LOGIN", "SELECT * FROM MAPSKILLS.LOGIN WHERE ID > 2 ORDER BY USERNAME", new String[]{"ID", "PASSWORD"});
		verifyDatasetForTable("student/imported-students-incomplete-file.xml", "STUDENT", "SELECT * FROM MAPSKILLS.STUDENT ORDER BY RA", new String[]{"ID", "ID_LOGIN"});
	}
	
	@Test
	@WithMockUser
	public void saveStudent() throws Exception {
		final String studentJson = getJsonAsString("json/request/student/new-student.json");
		performPost(mvc, "/student", studentJson).andExpect(status().isOk());
		verifyDatasetForTable("student/created-student.xml", "LOGIN", "SELECT * FROM MAPSKILLS.LOGIN ORDER BY ID", new String[]{"PASSWORD"});
		verifyDatasetForTable("student/created-student.xml", "STUDENT", "SELECT * FROM MAPSKILLS.STUDENT", new String[]{"ID"});
	}
	
	@Test
	@WithMockUser
	public void saveStudentAlreadExisting() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student.sql");
		final String studentJson = getJsonAsString("json/request/student/student-already-existing.json");
		final String jsonExpected = getJsonAsString("json/expectations/student/student-already-exists-exception.json");
		final MvcResult result = performPost(mvc, "/student", studentJson).andExpect(status().isPreconditionFailed()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, false);
	}
	
	@Test
	@WithMockUser
	public void saveStudentInvalidAcademicRegistry() throws Exception {
		final String studentJson = getJsonAsString("json/request/student/student-with-invalid-ra.json");
		final String jsonExpected = getJsonAsString("json/expectations/student/student-academic-registry-exception.json");
		final MvcResult result = performPost(mvc, "/student", studentJson).andExpect(status().isBadRequest()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, false);
	}
	
	@Test
	@WithMockUser
	public void updateStudent() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student.sql");
		final String studentJson = getJsonAsString("json/request/student/student-update.json");
		performPut(mvc, "/student/3", studentJson).andExpect(status().isOk());
		verifyDatasetForTable("student/updated-student.xml", "LOGIN", "SELECT * FROM MAPSKILLS.LOGIN WHERE ID = 3", new String[]{"PASSWORD"});
		verifyDatasetForTable("student/updated-student.xml", "STUDENT", "SELECT * FROM MAPSKILLS.STUDENT WHERE ID = 3", new String[]{});
	}
	
	@Test
	@WithMockUser
	public void saveAnswer() throws Exception {
		doNothing().when(threadPool).execute(any(Runnable.class));
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/update-institution-set-theme.sql");
		final String answerJson = getJsonAsString("json/request/student/new-answer.json");
		performPost(mvc, "/student/game/answer", answerJson).andExpect(status().isOk());
		verifyDatasetForTable("student/question-answered.xml", "STUDENT_QUESTION_CONTEXT", "SELECT * FROM MAPSKILLS.STUDENT_QUESTION_CONTEXT", new String[]{"ID", "DATE"});
	}
	
	@Test
	@WithMockUser
	public void saveAnswerWithRemainingQuestion() throws Exception {
		doNothing().when(threadPool).execute(any(Runnable.class));
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes-with-question.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/update-institution-set-theme.sql");
		final String answerJson = getJsonAsString("json/request/student/new-answer-with-remaining-question.json");
		performPost(mvc, "/student/game/answer", answerJson).andExpect(status().isOk());
		verifyDatasetForTable("student/question-answered.xml", "STUDENT_QUESTION_CONTEXT", "SELECT * FROM MAPSKILLS.STUDENT_QUESTION_CONTEXT", new String[]{"ID", "DATE"});
	}
	
	@Test
	@WithMockUser
	public void getScenesNotAnswered() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/update-institution-set-theme.sql");
		final String jsonExpected = getJsonAsString("json/expectations/student/scenes-not-answered.json");
		final MvcResult result = performGet(mvc, "/student/3/scene").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void getScenesPartiallyAnswered() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/update-institution-set-theme.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student-question-context.sql");
		final String jsonExpected = getJsonAsString("json/expectations/student/scenes-partially-answered.json");
		final MvcResult result = performGet(mvc, "/student/3/scene").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void getScenesWithThemeDisabled() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/insert-student.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/student/update-institution-set-theme-disabled.sql");
		final MvcResult result = performGet(mvc, "/student/3/scene").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals("[]", jsonResult, true);
	}
}