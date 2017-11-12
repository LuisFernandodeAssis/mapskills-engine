/*
 * @(#)SkillControllerTest.java 1.0 1 11/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import br.gov.sp.fatec.mapskills.config.AbstractIntegrationTest;

/**
 * A classe {@link SkillControllerTest}
 *
 * @author Marcelo
 * @version 1.0 11/11/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MapSkillsApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SkillControllerTest extends AbstractIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Test
	@WithMockUser
	public void getAllSkills() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/insert-skills.sql");
		final String jsonExpected = getJsonAsString("skills.json");
		final MvcResult result = this.mvc.perform(get("/skills"))
				.andExpect(status().isOk())
				.andReturn();

		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}

}