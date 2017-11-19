/*
 * @(#)MentorSerializer.java 1.0 31/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link MentorSerializer} e responsavel
 * por serializar o perfil <i>Mentor</i> da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 31/12/2016
 */
@Component
@AllArgsConstructor
public class MentorSerializer extends DefaultUserSerializer<Mentor> {
	
	private final InstitutionSerializer institutionSerializer;
	
	@Override
	public void serialize(final Mentor mentor, final Enum<?> arg1, final JsonWriter writer) throws IOException {
		super.serialize(mentor, null, writer);
		writer.writeObjectFieldStart(SerializationKey.INSTITUTION);
		institutionSerializer.serialize(mentor.getInstitution(), writer);
		writer.writeEndObject();
	}
}