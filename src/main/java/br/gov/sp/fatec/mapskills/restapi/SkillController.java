/*
 * @(#)SkillController.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.SkillApplicationServices;
import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.restapi.wrapper.ListWrapper;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SkillWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link SkillController}
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@RestController
@AllArgsConstructor
public class SkillController {
	
	private final SkillApplicationServices applicationServices;
	
	/**
	 * End-point reponsavel por criar uma nova competencia na aplicacao.
	 * 
	 * @param skillWrapper Competencia a ser cadastrada.
	 */
	@PostMapping("/skill")
	public void createSkill(@RequestBody final SkillWrapper skillWrapper) {
		applicationServices.save(skillWrapper.getSkill());
	}
	
	/**
	 * End-point que expoe servico para recuperar todas as competencias
	 * cadastradas na aplicacao.
	 * 
	 * @return Todas as competencias.
	 */
	@GetMapping("/skills")
	public ListWrapper<Skill> getAllSkills() {
		return new ListWrapper<Skill>(applicationServices.findAll()); 
	}
	
	/**
	 * End-point reponsavel por atualizar as informcoes de uma competencia.
	 * 
	 * @param id Identificador da competencia.
	 * @param skillWrapper Habilidade a ser atualizada.
	 */
	@PutMapping("/skill/{id}")
	public void updateSkill(@PathVariable("id") final Long id,
			@RequestBody final SkillWrapper skillWrapper) {
		applicationServices.updateSkill(id, skillWrapper.getSkill());
	}
}