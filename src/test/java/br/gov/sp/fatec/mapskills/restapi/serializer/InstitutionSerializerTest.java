/*
 * @(#)InstitutionSerializerTest.java 1.0 22/04/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionWrapperTest;
/**
 * 
 * A classe {@link InstitutionSerializerTest}
 *
 * @author Marcelo
 * @version 1.0 22/04/2017
 */
public class InstitutionSerializerTest extends JsonSerializer<InstitutionWrapperTest> {

	@Override
	public void serialize(final InstitutionWrapperTest wrapper, final JsonGenerator generator,
			final SerializerProvider arg2)	throws IOException {
		
		final Institution institution = wrapper.getInstitution();
		generator.writeStartObject();
		generator.writeStringField("code", institution.getCode());
		generator.writeStringField("cnpj", institution.getCnpj());
		generator.writeStringField("company", institution.getCompany());
		generator.writeStringField("level", institution.getLevel().name());
		generator.writeStringField("city", institution.getCity());
		mentorSerializer(wrapper.getMentor(), generator);
		generator.writeEndObject();

	}
	
	private void mentorSerializer(final Mentor mentor, final JsonGenerator generator) throws IOException {
		generator.writeObjectFieldStart("mentor");
		generator.writeStringField("name", mentor.getName());
		generator.writeStringField("username", mentor.getUsername());
		generator.writeEndObject();
	}

}
