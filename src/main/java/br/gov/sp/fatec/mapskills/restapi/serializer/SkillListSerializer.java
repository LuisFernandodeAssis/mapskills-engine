/*
 * @(#)SkillSerializer.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SkillListWrapper;

/**
 * 
 * A classe {@link SkillListSerializer} serializa uma lista de competencias.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
public class SkillListSerializer extends AbstractJsonSerializer<SkillListWrapper> {

	@Override
	public void serialize(final SkillListWrapper skillsWrapper, final JsonGenerator generator) throws IOException {
		generator.writeStartArray();
		for (final Skill skill : skillsWrapper.getSkills()) {
			generator.writeStartObject();
			generator.writeNumberField("id", skill.getId());
			generator.writeStringField("type", skill.getName());
			generator.writeStringField("description", skill.getDescription());
			generator.writeEndObject();
		}
		generator.writeEndArray();
	}
}