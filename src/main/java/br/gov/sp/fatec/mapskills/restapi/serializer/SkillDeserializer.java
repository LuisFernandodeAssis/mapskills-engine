/*
 * @(#)SkillDeserializer.java 1.0 01/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.skill.Skill;
import br.gov.sp.fatec.mapskills.restapi.wrapper.SkillWrapper;

/**
 * 
 * A classe {@link SkillDeserializer} e responsavel
 * por deserializar um <code>Skill</code> (competencia)
 * para que seja cadastrada ou atualizada.
 *
 * @author Marcelo
 * @version 1.0 01/11/2016
 */
public class SkillDeserializer extends AbstractJsonDeserializer<SkillWrapper> {

	@Override
	protected SkillWrapper deserialize(final JsonNode node) {
		return new SkillWrapper(new Skill(getFieldTextValue(node, SerializationKey.TYPE),
				getFieldTextValue(node, SerializationKey.DESCRIPTION)));
	}
}