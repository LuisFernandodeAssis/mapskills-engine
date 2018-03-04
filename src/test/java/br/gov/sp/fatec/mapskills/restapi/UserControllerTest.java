/*
 * @(#)UserControllerTest.java 1.0 1 24/02/2018
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.gov.sp.fatec.mapskills.MapSkillsApplication;
import br.gov.sp.fatec.mapskills.application.UserApplicationServices;
import br.gov.sp.fatec.mapskills.domain.user.User;

/**
 * A classe {@link UserControllerTest}
 *
 * @author Marcelo
 * @version 1.0 24/02/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MapSkillsApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest extends AbstractIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private UserApplicationServices services;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Test
	@WithMockUser
	public void getAdminUser() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/user/insert-admin-user.sql");
		final String jsonExpected = getJsonAsString("json/expectations/user/admin-user.json");
		final MvcResult result = performGet(mvc, "/user?username=admin@cps.sp.gov.br").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void getMentorUser() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/user/insert-mentor-user.sql");
		final String jsonExpected = getJsonAsString("json/expectations/user/mentor-user.json");
		final MvcResult result = performGet(mvc, "/user?username=mentor@fatec.sp.gov.br").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser(username = "mentor@fatec.sp.gov.br")
	public void getStudentUser() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/user/insert-student-user.sql");
		final String jsonExpected = getJsonAsString("json/expectations/user/student-user.json");
		final MvcResult result = performGet(mvc, "/user?username=alana.manuela.lima@fatec.sp.gov.br").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void changePassword() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/user/insert-student-user.sql");
		final String username = "alana.manuela.lima@fatec.sp.gov.br";
		final String newPassword = URLEncoder.encode("4L4N4#L1M4", "UTF-8");
		performPut(mvc, String.format("/user?username=%s&newPassword=%s", username, newPassword)).andExpect(status().isOk());
		final User alana = services.findUserByUsername(username);
		assertTrue(encoder.matches("4L4N4#L1M4", alana.getPassword()));
	}
}