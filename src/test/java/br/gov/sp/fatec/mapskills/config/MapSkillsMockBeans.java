/*
 * @(#)MapSkillsMockBeans.java 1.0 08/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
import br.gov.sp.fatec.mapskills.domain.scene.Alternative;
import br.gov.sp.fatec.mapskills.domain.scene.Question;
import br.gov.sp.fatec.mapskills.domain.scene.Scene;
import br.gov.sp.fatec.mapskills.domain.scene.SceneService;
import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.skill.SkillService;
import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.domain.theme.GameThemeService;
import br.gov.sp.fatec.mapskills.domain.user.Administrator;
import br.gov.sp.fatec.mapskills.domain.user.UserRepository;
import br.gov.sp.fatec.mapskills.domain.user.mentor.Mentor;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
/**
 * 
 * A classe {@link MapSkillsMockBeans} contem metodos
 * que retornam instancias dos objetos de dominio
 * para que sejam feitos testes.
 *
 * @author Marcelo
 * @version 1.0 08/01/2017
 */
@Configuration
public class MapSkillsMockBeans {
	
	private static final String SUCCESS = "SUCCESS"; 
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private GameThemeService themeService;
	
	@Autowired
	private SceneService sceneService;
	
	@Autowired
	private SkillService skillService;
	
	@Bean
	public String saveAdmin() {
		final Administrator admin = new Administrator("Administrador", "admin@cps.sp.gov.br", "admin");
		userRepo.save(admin);
		return SUCCESS;
	}
	
	@Bean
	public String saveInstitution() {
		final List<Institution> institutions = new ArrayList<>(1);
		final Institution fatecA = new Institution("146", "60565187000100", "Jessen Vidal", InstitutionLevel.SUPERIOR, "S�o Jos�", null, null);
		fatecA.addMentor(new Mentor("Marquinhos", "marquinhos@cps.sp.gov.br", "mudar@123", fatecA));
		institutions.add(fatecA);
		institutionService.saveInstitutions(institutions);
		
		return SUCCESS;
	}
	
	@Bean
	public String saveStudent() throws MapSkillsException {
		final Student student = new Student("1460281423050", "Student MockE", "1289003400", "student@fatec.sp.gov.br", "mudar@123");
		institutionService.saveStudent(student);
		return SUCCESS;
	}
	
	@Bean
	public String saveGameTheme() {
		themeService.save(new GameTheme("pizzaria, aplicado em 2016/2"));
		themeService.save(new GameTheme("empresa de musica, aplicado em 2017/1"));
		return SUCCESS;
	}
	
	@Bean
	public String saveScenes() {
		final long themeId = 1L;
		final long skillId = 1L;
		final List<Alternative> alternatives = new ArrayList<>(4);
		alternatives.addAll(builderMockAlternatives());
		final Question question = new Question(null, alternatives, skillId);

		sceneService.save(new Scene(null, null, "introdu��o", "url://site/img001.png", null, themeId));
		sceneService.save(new Scene(null, null, "quest�o", "url://site/img002.png", question, themeId));
		sceneService.save(new Scene(null, null, "conclus�o", "url://site/img003.png", null, themeId));
		
		return SUCCESS;
	}
	
	@Bean
	public String saveSkills() {		
		skillService.save(new Skill("Comunica��o", "Avalia a dic��o do aluno"));
		skillService.save(new Skill("Lideran�a", "Avalia a lideran�a do aluno"));
		skillService.save(new Skill("Gest�o de Tempo", "Avalia a gest�o de tempo do aluno"));
		
		return SUCCESS;
	}
	
	private Collection<Alternative> builderMockAlternatives() {
		final Collection<Alternative> alternatives = new ArrayList<>(4);
		alternatives.add(new Alternative(null, "AlternativaMockA", 8));
		alternatives.add(new Alternative(null, "AlternativaMockB", 5));
		alternatives.add(new Alternative(null, "AlternativaMockC", 6));
		alternatives.add(new Alternative(null, "AlternativaMockD", 4));
		return alternatives;
	}

}
