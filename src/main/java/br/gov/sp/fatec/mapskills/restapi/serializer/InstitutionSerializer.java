/*
 * @(#)InstitutionDetailsSerializer.java 1.0 07/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionWrapper;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link InstitutionSerializer} e responsavel
 * por serializar algumas informacaoes adicionais sobre uma determinada
 * instituicao.
 *
 * @author Marcelo
 * @version 1.0 07/01/2017
 */
@Component
@AllArgsConstructor
public class InstitutionSerializer extends AbstractJsonSerializer<InstitutionWrapper> {
	
	private final DefaultInstitutionSerializer defaultSerializer;
	private final CourseSerializer courseSerializer;
	private final MentorSerializer mentorSerializer;
		
	@Override
	public void serialize(final InstitutionWrapper wrapper, final JsonGenerator generator) throws IOException {
		final Institution institution = wrapper.getInstitution();
		writeStartObject();
		defaultSerializer.serialize(institution, generator);
		courseListSerialize(institution, generator);
		mentorsSerialize(institution.getMentors(), generator);
		writeEndObject();
		
	}
	
	private void courseListSerialize(final Institution institution, final JsonGenerator generator) throws IOException {
		writeArrayFieldStart(SerializationKey.COURSES);
		for(final Course course : institution.getCourses()) {
			courseSerialize(course, generator);
		}
		writeEndArray();
	}
	
	private void courseSerialize(final Course course, final JsonGenerator generator) throws IOException {
		writeStartObject();
		courseSerializer.serialize(course, generator);
		writeEndObject();
	}
	
	private void mentorsSerialize(final List<Mentor> mentors, final JsonGenerator generator) throws IOException {
		writeArrayFieldStart(SerializationKey.MENTORS);
		for(final Mentor mentor : mentors) {
			mentorSerialize(mentor, generator);
		}
		writeEndArray();
	}
	
	private void mentorSerialize(final Mentor mentor, final JsonGenerator generator) throws IOException {
		writeStartObject();
		mentorSerializer.serializeInstitution(mentor);
		writeEndObject();
	}
}