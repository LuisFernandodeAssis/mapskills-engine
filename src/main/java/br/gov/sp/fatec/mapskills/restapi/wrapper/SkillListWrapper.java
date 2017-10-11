/*
 * @(#)SkillListWrapper.java 1.0 03/11/2016
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.restapi.serializer.SkillListSerializer;

/**
 * 
 * A classe {@link SkillListWrapper} encapsula uma colecao
 * de <code>Skill</code> (competencias) para que seja serializadas.
 *
 * @author Marcelo
 * @version 1.0 03/11/2016
 */
@JsonSerialize(using = SkillListSerializer.class)
public class SkillListWrapper {
	
	private final List<Skill> skills = new LinkedList<>();
	
	public SkillListWrapper(final List<Skill> skills) {
		this.skills.addAll(skills);
	}
	
	public List<Skill> getSkills() {
		return Collections.unmodifiableList(skills);
	}
}