/*
 * @(#)SkillApplicationServices.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.application;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.domain.skill.SkillRepository;
import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link SkillApplicationServices}
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@ApplicationServices
@AllArgsConstructor
public class SkillApplicationServices {

	private final SkillRepository repository;

	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void save(final Skill skill) {
		repository.save(skill);
	}
	
	@PreAuthorize("isFullyAuthenticated()")
	public List<Skill> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	@Transactional
	@PreAuthorize("isFullyAuthenticated()")
	public void updateSkill(final Long id, final Skill updateSkill) {
		final Skill skill = repository.findOne(id);
		skill.update(updateSkill);
		repository.save(skill);
	}
}