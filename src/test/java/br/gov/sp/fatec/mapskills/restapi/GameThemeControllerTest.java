/*
 * @(#)GameThemeControllerTest.java 1.0 1 11/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
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
import br.gov.sp.fatec.mapskills.utils.ThreadPool;

/**
 * A classe {@link GameThemeControllerTest}
 *
 * @author Marcelo
 * @version 1.0 11/11/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MapSkillsApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GameThemeControllerTest extends AbstractIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ThreadPool threadPool;
	
	@Test
	@WithMockUser
	public void createTheme() throws Exception {
		final String themeJson = getJsonAsString("json/request/theme/new-theme.json");
		performPost(mvc, "/game/theme", themeJson).andExpect(status().isOk());
		verifyDatasetForTable("theme/created-theme.xml", "GAME_THEME", "SELECT * FROM MAPSKILLS.GAME_THEME", new String[]{"ID"});		
	}
	
	@Test
	@WithMockUser
	public void getThemesDontOnlyActives() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		final String jsonExpected = getJsonAsString("json/expectations/theme/themes-dont-only-actives.json");
		final MvcResult result = performGet(mvc, "/game/themes?onlyActives=false").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void getThemesOnlyActives() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		final String jsonExpected = getJsonAsString("json/expectations/theme/themes-only-actives.json");
		final MvcResult result = performGet(mvc, "/game/themes?onlyActives=true").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void createScene() throws Exception {
		doNothing().when(threadPool).execute(any(Runnable.class));	
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		final String sceneJson = getJsonAsString("json/request/theme/new-scene.json");
		performPost(mvc, "/game/1/scene", sceneJson).andExpect(status().isOk());		
		verifyDatasetForTable("theme/created-scene.xml", "SCENE", "SELECT * FROM MAPSKILLS.SCENE", new String[]{"ID"});
	}
	
	@Test
	@WithMockUser
	public void getAllScenesByThemeId() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		final String jsonExpected = getJsonAsString("json/expectations/theme/scenes-of-theme.json");
		final MvcResult result = performGet(mvc, "/game/theme/5").andExpect(status().isOk()).andReturn();
		final String jsonResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(jsonExpected, jsonResult, true);
	}
	
	@Test
	@WithMockUser
	public void deleteQuestion() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		performDelete(mvc, "/game/5/scene/2/question").andExpect(status().isOk());
		verifyDatasetForTable("theme/deleted-question-scene.xml", "SCENE", "SELECT * FROM MAPSKILLS.SCENE", new String[]{"ID"});
	}
	
	@Test
	@WithMockUser
	public void deleteScene() throws Exception {
		doNothing().when(threadPool).execute(any(Runnable.class));
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		performDelete(mvc, "/game/5/scene/3").andExpect(status().isOk());
		verifyDatasetForTable("theme/deleted-scene.xml", "SCENE", "SELECT * FROM MAPSKILLS.SCENE", new String[]{"ID"});
	}
	
	@Test
	@WithMockUser
	public void updateScene() throws Exception {
		doNothing().when(threadPool).execute(any(Runnable.class));
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		final String sceneJson = getJsonAsString("json/request/theme/update-scene.json");
		performPut(mvc, "/game/5/scene/3", sceneJson).andExpect(status().isOk());
		verifyDatasetForTable("theme/updated-scene.xml", "SCENE", "SELECT * FROM MAPSKILLS.SCENE ORDER BY _INDEX", new String[]{"ID"});
		verifyDatasetForTable("theme/updated-scene.xml", "ALTERNATIVE", "SELECT * FROM MAPSKILLS.ALTERNATIVE ORDER BY ID_QUESTION ASC, DESCRIPTION ASC", new String[]{"ID"});
		verifyDatasetForTable("theme/updated-scene.xml", "QUESTION", "SELECT * FROM MAPSKILLS.QUESTION ORDER BY ID", new String[]{"ID"});
	}
	
	@Test
	@WithMockUser
	public void updateSceneWithoutQuestionWithImage() throws Exception {
		doNothing().when(threadPool).execute(any(Runnable.class));
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		final String sceneJson = getJsonAsString("json/request/theme/update-scene-without-question.json");
		performPut(mvc, "/game/5/scene/1", sceneJson).andExpect(status().isOk());
		verifyDatasetForTable("theme/updated-scene-without-question.xml", "SCENE", "SELECT * FROM MAPSKILLS.SCENE WHERE ID = 1", new String[]{});
	}
	
	@Test
	@WithMockUser
	public void updateSceneWithQuestion() throws Exception {
		doNothing().when(threadPool).execute(any(Runnable.class));
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		final String sceneJson = getJsonAsString("json/request/theme/update-scene-with-question.json");
		performPut(mvc, "/game/5/scene/2", sceneJson).andExpect(status().isOk());
		verifyDatasetForTable("theme/updated-scene-with-question.xml", "SCENE", "SELECT * FROM MAPSKILLS.SCENE ORDER BY ID", new String[]{});
		verifyDatasetForTable("theme/updated-scene-with-question.xml", "ALTERNATIVE", "SELECT * FROM MAPSKILLS.ALTERNATIVE ORDER BY DESCRIPTION", new String[]{"ID"});
		verifyDatasetForTable("theme/updated-scene-with-question.xml", "QUESTION", "SELECT * FROM MAPSKILLS.QUESTION", new String[]{});
	}
	
	@Test
	@WithMockUser
	public void updateIndexScenes() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-scenes.sql");
		final String sceneJson = getJsonAsString("json/request/theme/update-scene-index.json");
		performPut(mvc, "/game/5/scenes", sceneJson).andExpect(status().isOk());
		verifyDatasetForTable("theme/updated-scene-index.xml", "SCENE", "SELECT * FROM MAPSKILLS.SCENE ORDER BY _INDEX", new String[]{"ID"});
	}
	
	@Test
	@WithMockUser
	public void updateGameThemeStatus() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		performPut(mvc, "/game/1?status=true").andExpect(status().isOk());
		performPut(mvc, "/game/2?status=true").andExpect(status().isOk());
		performPut(mvc, "/game/4?status=false").andExpect(status().isOk());
		verifyDatasetForTable("theme/updated-theme-status.xml", "GAME_THEME", "SELECT * FROM MAPSKILLS.GAME_THEME ORDER BY ID", new String[]{});
	}
	
	@Test
	public void updateGameThemeStatusNonLogged() throws Exception {
		runSQLCommands("/br/gov/sp/fatec/mapskills/database/controller/theme/insert-themes.sql");
		performPut(mvc, "/game/1?status=true").andExpect(status().isForbidden());
	}
}