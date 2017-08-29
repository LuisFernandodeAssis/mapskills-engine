/*
 * @(#)InstitutionDetailsSerializer.java 1.0 07/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.user.mentor.Mentor;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionDetailsWrapper;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link InstitutionDetailsSerializer} e responsavel
 * por serializar algumas informacaoes adicionais sobre uma determinada
 * instituicao.
 *
 * @author Marcelo
 * @version 1.0 07/01/2017
 */
@Component
@AllArgsConstructor
public class InstitutionDetailsSerializer extends AbstractInstitutionSerializer<InstitutionDetailsWrapper> {
	
	private final CourseSerializer courseSerializer;
		
	@Override
	public void serialize(final InstitutionDetailsWrapper detailsWrapper, final JsonGenerator generator) throws IOException {

		final Institution institution = detailsWrapper.getInstitution();
		
		generator.writeStartObject();
		super.defaultSerializer.serializeDefaultValues(institution, generator);
		this.courseListSerialize(institution, generator);
		this.mentorsSerialize(institution.getMentors(), generator);
		generator.writeEndObject();
		
	}
	
	private void courseListSerialize(final Institution institution, final JsonGenerator generator) throws IOException {
		generator.writeArrayFieldStart("courses");
		for(final Course course : institution.getCourses()) {
			this.courseSerialize(course, generator);
		}
		generator.writeEndArray();
	}
	
	private void courseSerialize(final Course course, final JsonGenerator generator) throws IOException {
		generator.writeStartObject();
		courseSerializer.serialize(course, generator);
		generator.writeEndObject();
	}
	
	private void mentorsSerialize(final Collection<Mentor> mentors, final JsonGenerator generator) throws IOException {
		generator.writeArrayFieldStart("mentors");
		for(final Mentor mentor : mentors) {
			this.mentorSerialize(mentor, generator);
		}
		generator.writeEndArray();
	}
	
	private void mentorSerialize(final Mentor mentor, final JsonGenerator generator) throws IOException {
		generator.writeStartObject();
		generator.writeNumberField("id", mentor.getId());
		generator.writeStringField("name", mentor.getName());
		generator.writeStringField("institutionCode", mentor.getInstitutionCode());
		generator.writeStringField("username", mentor.getUsername());
		generator.writeStringField(DefaultJsonSerializer.PASS, DefaultJsonSerializer.EMPTY_PASS);
		generator.writeEndObject();
	}
}