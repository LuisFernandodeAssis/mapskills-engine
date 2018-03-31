/*
 * @(#)SkillControllerTest.java 1.0 1 11/11/2017
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
import br.gov.sp.fatec.mapskills.domain.skill.Skill;

/**
 * A classe {@link SkillControllerTest} contem testes de integracao para
 * funcionalidades referentes o aggregate {@link Skill}
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
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/skill/insert-skills.sql");
		final String jsonExpected = getJsonAsString("json/expectations/skill/skills.json");
		final MvcResult result = performGet(mvc, "/skills").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void createSkill() throws Exception {
		final String skillJson = getJsonAsString("json/request/skill/new-skill.json");
		performPost(mvc, "/skill", skillJson).andExpect(status().isOk());
		verifyDatasetForTable("skill/created-skill.xml", "SKILL", "SELECT * FROM MAPSKILLS.SKILL", new String[]{"ID"});
	}
	
	@Test
	@WithMockUser
	public void updateSkill() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/skill/insert-skills.sql");
		final String skillJson = getJsonAsString("json/request/skill/skill-update.json");
		performPut(mvc, "/skill/1", skillJson).andExpect(status().isOk());
		verifyDatasetForTable("skill/updated-skill.xml", "SKILL", "SELECT * FROM MAPSKILLS.SKILL WHERE ID = 1", new String[]{});
	}
}