/*
 * @(#)MapSkillsMockBeans.java 1.0 08/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.Fatec-Jessen Vidal 
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.sp.fatec.mapskills.application.MapSkillsException;
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
import br.gov.sp.fatec.mapskills.domain.user.student.AcademicRegistry;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;

@Configuration
public class MapSkillsMockBeans {
	
	private static final String MESSAGE = "SUCCESS"; 
	
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
		return MESSAGE;
	}
	
	@Bean
	public String saveInstitution() {
		final Collection<Institution> institutions = new ArrayList<>(1);
		final Institution fatecA = new Institution("146", "60565187000100", "Jessen Vidal", InstitutionLevel.SUPERIOR,"S�o Jos�");
		fatecA.addMentor(new Mentor("Marquinhos", "146", "marquinhos@cps.sp.gov.br", "mudar@123"));
		institutions.add(fatecA);
		institutionService.saveInstitutions(institutions);
		
		return MESSAGE;
	}
	
	@Bean
	public String saveStudent() throws MapSkillsException {
		final Student student = new Student(new AcademicRegistry("1460281423050", "146", "028"), "Student MockE", "1289003400", "student@fatec.sp.gov.br", "mudar@123");
		institutionService.saveStudent(student);
		return MESSAGE;
	}
	
	@Bean
	public String saveGameTheme() {
		final GameTheme themeA = new GameTheme("pizzaria, aplicado em 2016/2");
		themeService.save(themeA);
		final GameTheme themeB = new GameTheme("empresa de musica, aplicado em 2017/1");
		themeService.save(themeB);
		return MESSAGE;
	}
	
	@Bean
	public String saveScenes() {
		final int THEME_ID = 1;
		final int SKILL_ID = 1;
		final Collection<Alternative> alternatives = new ArrayList<>(4);
		alternatives.addAll(builderMockAlternatives());
		final Question question = new Question(alternatives, SKILL_ID);
		
		final Scene scene0 = new Scene("introdu��o", "url://site/img001.png", null, THEME_ID);
		sceneService.save(scene0);
		
		final Scene scene1 = new Scene("quest�o", "url://site/img002.png", question, THEME_ID);
		sceneService.save(scene1);
		
		final Scene scene2 = new Scene("conclus�o", "url://site/img003.png", null, THEME_ID);
		sceneService.save(scene2);
		
		return MESSAGE;
	}
	
	@Bean
	public String saveSkills() {
		final Skill comunicacao = new Skill("Comunica��o", "Avalia a dic��o do aluno");
		final Skill lideranca = new Skill("Lideran�a", "Avalia a lideran�a do aluno");
		final Skill tempo = new Skill("Gest�o de Tempo", "Avalia a gest�o de tempo do aluno");
		skillService.save(comunicacao);
		skillService.save(lideranca);
		skillService.save(tempo);
		
		return MESSAGE;
	}
	
	private Collection<Alternative> builderMockAlternatives() {
		final Collection<Alternative> alternatives = new ArrayList<>(4);
		final Alternative a = new Alternative("AlternativaMockA", 8);
		final Alternative b = new Alternative("AlternativaMockB", 5);
		final Alternative c = new Alternative("AlternativaMockC", 6);
		final Alternative d = new Alternative("AlternativaMockD", 4);
		alternatives.add(a);
		alternatives.add(b);
		alternatives.add(c);
		alternatives.add(d);
		return alternatives;
	}

}
