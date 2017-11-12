/*
 * @(#)SkillWrapper.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.restapi.serializer.SkillDeserializer;
/**
 * 
 * A classe {@link SkillWrapper} encapsula um <code>Skill</code>
 * na deserializacao, para que seja cadastrada ou atualizada.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@JsonDeserialize(using = SkillDeserializer.class)
public class SkillWrapper {
	
	private final Skill skill;
	
	public SkillWrapper(final Skill skill) {
		this.skill = skill;
	}
	
	public Skill getSkill() {
		return skill;
	}
}