/*
 * @(#)SkillSerializer.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;

/**
 * 
 * A classe {@link SkillSerializer} serializa uma lista de competencias.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
@Component
public class SkillSerializer extends AbstractSerializer<Skill> {

	@Override
	public void serialize(final Skill skill, final Enum<?> arg1, final JsonWriter writer) throws IOException {
		writer.writeNumberField(SerializationKey.ID, skill.getId());
		writer.writeStringField(SerializationKey.NAME, skill.getName());
		writer.writeStringField(SerializationKey.DESCRIPTION, skill.getDescription());		
	}
}