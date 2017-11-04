/*
 * @(#)SkillRepository.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.domain.skill;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
/**
 * 
 * A classe {@link SkillRepository}
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
public interface SkillRepository extends CrudRepository<Skill, Long> {
	
	Skill findById(final Long id);
	List<Skill> findAllByOrderByNameAsc();
}