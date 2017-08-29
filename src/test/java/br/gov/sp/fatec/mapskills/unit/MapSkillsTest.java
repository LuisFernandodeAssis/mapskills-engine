/*
 * @(#)MapSkillsTest.java 1.0 29/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.unit;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.gov.sp.fatec.mapskills.config.AbstractIntegrationTest;
import br.gov.sp.fatec.mapskills.config.SpringContextTestConfiguration;
import br.gov.sp.fatec.mapskills.config.WebConfig;
import br.gov.sp.fatec.mapskills.domain.scene.Alternative;
import br.gov.sp.fatec.mapskills.domain.scene.Question;
import br.gov.sp.fatec.mapskills.domain.scene.Scene;
import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.skill.SkillService;
/**
 * 
 * A classe {@link MapSkillsTest} possui metodos
 * para criacao de objetos de dominio para realizacao
 * de testes.
 *
 * @author Marcelo
 * @version 1.0 29/12/2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class})
@WebAppConfiguration
public abstract class MapSkillsTest extends AbstractIntegrationTest {
	
	@Autowired
	protected SkillService skillService;
		
	protected List<Scene> buildMockScenes(final long themeId) {
		final List<Alternative> alternatives = builderMockAlternatives(); 
		final List<Scene> scenes = new ArrayList<>(6);
		
		scenes.add(new Scene(null, null, "olá sente-se", "/scenes/bg001.png", null, themeId));
		scenes.add(new Scene(null, null, "me diga um qualidade", "/scenes/bg002.png", new Question(null, alternatives, 1L), themeId));
		scenes.add(new Scene(null, null, "muito bom", "/scenes/bg003.png", null, themeId));
		scenes.add(new Scene(null, null, "qual sua primeira atitude?", "/scenes/bg004.png", null, themeId));
		scenes.add(new Scene(null, null, "qual sua primeira atitude?", "/scenes/bg005.png", new Question(null, alternatives, 2L), themeId));
		scenes.add(new Scene(null, null, "bem pensado!", "/scenes/bg006.png", null, themeId));
		
		return scenes;
	}
	
	protected List<Alternative> builderMockAlternatives() {
		final List<Alternative> alternatives = new ArrayList<>(4);
		alternatives.add(new Alternative(null, "AlternativaMockA", 8));
		alternatives.add(new Alternative(null, "AlternativaMockB", 5));
		alternatives.add(new Alternative(null, "AlternativaMockC", 6));
		alternatives.add(new Alternative(null, "AlternativaMockD", 4));
		return alternatives;
	}
	
	protected List<Skill> buildSkillsMock() {
		final List<Skill> skills = new ArrayList<>(4);
		skills.add(new Skill("Visão do futuro", ""));
		skills.add(new Skill("Comunicação", ""));
		skills.add(new Skill("Gestão do tempo", ""));
		skills.add(new Skill("Equilibrio emocional", ""));
		return skills;
	}

}
