/*
 * @(#)MentorSerializer.java 1.0 31/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
/**
 * 
 * A classe {@link MentorSerializer} e responsavel
 * por serializar o perfil <i>Mentor</i> da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 31/12/2016
 */
@Component
public class MentorSerializer extends DefaultUserSerializer<Mentor> {
	
	@Override
	public void serialize(final Mentor mentor, final JsonGenerator generator) throws IOException {
		setGenerator(generator);
		writeStartObject();
		serializeDefaultValues(mentor);
		serializeInstitution(mentor);
		writeEndObject();
	}
	
	public void serializeInstitution(final Mentor mentor) throws IOException {
		final Institution institution = mentor.getInstitution();
		writeObjectFieldStart(SerializationKey.INSTITUTION);
		writeNumberField(SerializationKey.ID, institution.getId());
		writeStringField(SerializationKey.CODE, institution.getCode());
		writeStringField(SerializationKey.LEVEL, institution.getLevel());
		writeNumberField(SerializationKey.GAME_THEME_ID, institution.getGameThemeId());
		writeEndObject();
	}
}