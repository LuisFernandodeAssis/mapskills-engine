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

import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionService;
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

	private final InstitutionService service;
	
	@Override
	public void serialize(final Mentor mentor, final JsonGenerator generator) throws IOException {
		generator.writeStartObject();
		serializeCore(mentor);
		generator.writeEndObject();
	}
	
	public void serializeCore(final Mentor mentor) throws IOException {
		serializeDefaultValues(mentor);
		writeStringField(SerializationKey.INSTITUTION_CODE, mentor.getInstitutionCode());
		writeNumberField(SerializationKey.INSTITUTION_ID, mentor.getInstitutionId());
		writeStringField(SerializationKey.INSTITUTION_LEVEL, getLevel(mentor.getInstitutionCode()));
	}
	
	/**
	 * 
	 * @param institutionCode
	 * @return O nível da instituição que o mentor pertence.
	 */
	private InstitutionLevel getLevel(final String institutionCode) {
		return service.findInstitutionByCode(institutionCode).getLevel();
	}

}