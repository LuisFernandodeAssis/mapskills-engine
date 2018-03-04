/*
 * @(#)StudentControllerTest.java 1.0 1 11/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.gov.sp.fatec.mapskills.MapSkillsApplication;

/**
 * A classe {@link StudentControllerTest}
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
	
	@Test
	@WithMockUser
	public void saveStudent() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-institution.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/institution/insert-course.sql");
		final String studentJson = getJsonAsString("json/request/student/new-student.json");
		performPost(mvc, "/student", studentJson).andExpect(status().isOk());
		verifyDatasetForTable("student/created-student.xml", "LOGIN", "SELECT * FROM MAPSKILLS.LOGIN ORDER BY ID", new String[]{"PASSWORD"});
		verifyDatasetForTable("student/created-student.xml", "STUDENT", "SELECT * FROM MAPSKILLS.STUDENT", new String[]{"ID"});		
	}

}