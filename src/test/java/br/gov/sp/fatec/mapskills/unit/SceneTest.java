/*
 * @(#)SceneTest.java 1.0 29/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.sp.fatec.mapskills.domain.scene.Alternative;
import br.gov.sp.fatec.mapskills.domain.scene.Question;
import br.gov.sp.fatec.mapskills.domain.scene.Scene;
import br.gov.sp.fatec.mapskills.domain.scene.SceneService;
import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeRepository;
/**
 * 
 * A classe {@link SceneTest} contem os testes
 * do aggregate <code>GameTheme</code>.
 *
 * @author Marcelo
 * @version 1.0 29/12/2016
 */
public class SceneTest extends MapSkillsTest {
	
	@Autowired
	private SceneService service;
	
	@Autowired
	private GameThemeRepository themeRepo;
	
	@Test
	public void save() {
		final long THEME_ID = 1L;
		final long SKILL_ID = 1L;
		final List<Alternative> alternatives = builderMockAlternatives();
		final Question question = new Question(null, alternatives, SKILL_ID);
		final Scene scene = new Scene(null, null, "intro", "url:site", question, THEME_ID);
		service.save(scene);
	}
	
	@Test
	public void saveList() {
		final long THEME_ID = 1L;
		final long SKILL_ID = 1L;
		final List<Alternative> alternatives = builderMockAlternatives();
		final Question question = new Question(null, alternatives, SKILL_ID);
		final Scene scene1 = new Scene(null, null, "texto 1", "/scenes/img001.png", null, THEME_ID);
		final Scene scene2 = new Scene(null, null, "questão", "/scenes/img002.png", question, THEME_ID);
		final Scene scene3 = new Scene(null, null, "texto 1", "/scenes/img003.png", null, THEME_ID);
		final List<Scene> scenes = new ArrayList<>(3);
		scenes.add(scene1);
		scenes.add(scene2);
		scenes.add(scene3);
		service.save(scenes);
			
		assertEquals(3, service.findAllByGameThemeId(THEME_ID).size());
	}
	
	@Test
	public void nextIndex() {
		
		final GameTheme themeA = themeRepo.save(new GameTheme("descrição tema 001"));
		final GameTheme themeB = themeRepo.save(new GameTheme("descrição tema 002"));
		
		final int index = service.nextIndex(themeA.getId());
		assertEquals(0, index);
		
		service.save(new Scene(null, null, "intro", "url:site", null, themeB.getId()));
		
		final int nextIndex = service.nextIndex(themeB.getId());
		
		assertEquals(1, nextIndex);
	}

	@Test
	public void update() {
		final long THEME_ID = 1L;
		final long SKILL_ID = 1L;
		final List<Alternative> alternatives = builderMockAlternatives();
		final Question question = new Question(null, alternatives, SKILL_ID);
		final Scene sceneSave2 = new Scene(null, null, "textoB", "/scenes/img005.png", question, THEME_ID);
		service.save(sceneSave2);
		
		final Scene sceneUpdate = new Scene(null, null, "TextoA", "/images/img001.png", null, THEME_ID);
		sceneUpdate.setId(sceneSave2.getId());
		service.save(sceneUpdate);

	}

}
