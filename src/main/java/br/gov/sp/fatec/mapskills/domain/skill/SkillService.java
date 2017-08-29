/*
 * @(#)SkillService.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.skill;

import java.util.Collection;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link SkillService}
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */

@Service
@AllArgsConstructor
public class SkillService {

	private final SkillRepository repository;

	public Skill save(final Skill skill) {
		return repository.save(skill);
	}
	
	public void update(final Skill skill) {
		repository.save(skill);
	}
	
	public Collection<Skill> findAll() {
		return repository.findAll();
	}

	public Skill findById(final long id) {
		return repository.findById(id);
	}
	
	public long getCount() {
		return repository.count();
	}

}
