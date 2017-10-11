/*
 * @(#)SkillController.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.application.SkillApplicationServices;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SkillListWrapper;
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
@RequestMapping(SkillController.BASE_PATH)
public class SkillController {
	
	public static final String BASE_PATH = "/admin";
	
	private final SkillApplicationServices skillApplicationServices;
	
	/**
	 * End-point reponsavel por criar uma nova competencia na aplicacao.
	 * 
	 * @param skillWrapper Competencia a ser cadastrada.
	 */
	@PostMapping(value = "/skill")
	public void createSkill(@RequestBody final SkillWrapper skillWrapper) {

		skillApplicationServices.save(skillWrapper.getSkill());

	}
	
	/**
	 * End-point que expoe servico para recuperar todas as competencias
	 * cadastradas na aplicacao.
	 * 
	 * @return Todas as competencias.
	 */
	@GetMapping(value = "/skills")
	public SkillListWrapper getAllSkills() {
		return new SkillListWrapper(skillApplicationServices.findAll()); 
	}
}