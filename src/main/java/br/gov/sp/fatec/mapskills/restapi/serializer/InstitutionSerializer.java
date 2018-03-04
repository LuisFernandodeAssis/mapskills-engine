/*
 * @(#)InstitutionSerializer.java 1.0 07/01/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.Mentor;
import lombok.AllArgsConstructor;

/**
 * A classe {@link InstitutionSerializer} e responsavel
 * por serializar algumas informacaoes adicionais sobre uma determinada
 * instituicao.
 *
 * @author Marcelo
 * @version 1.0 07/01/2017
 */
@Component
@AllArgsConstructor
public class InstitutionSerializer extends AbstractSerializer<Institution> {
	
	private final CourseSerializer courseSerializer;
	
	@SuppressWarnings("rawtypes")
	private final DefaultUserSerializer defaultUserSerializer;
	
	@Override
	public void serialize(final Institution institution, final Enum<?> arg1, final JsonWriter writer) throws IOException {
		this.serialize(institution, writer);
		coursesSerialize(institution.getCourses(), writer);
		mentorsSerialize(institution.getMentors(), writer);
	}
	
	@Override
	public void serialize(final Institution institution, final JsonWriter writer) throws IOException {
		writer.writeNumberField(SerializationKey.ID, institution.getId());
		writer.writeStringField(SerializationKey.CODE, institution.getCode());
		writer.writeNumberField(SerializationKey.CNPJ, institution.getCnpj());
		writer.writeStringField(SerializationKey.COMPANY, institution.getCompany());
		writer.writeStringField(SerializationKey.LEVEL, institution.getLevel());
		writer.writeStringField(SerializationKey.CITY, institution.getCity());
		writer.writeNumberField(SerializationKey.GAME_THEME_ID, institution.getGameThemeId());
	}
	
	private void coursesSerialize(final List<Course> courses, final JsonWriter writer) throws IOException {
		writer.writeArrayFieldStart(SerializationKey.COURSES);
		for(final Course course : courses) {
			writer.writeStartObject();
			courseSerializer.serialize(course, writer);
			writer.writeEndObject();
		}
		writer.writeEndArray();
	}
		
	@SuppressWarnings("unchecked")
	private void mentorsSerialize(final List<Mentor> mentors, final JsonWriter writer) throws IOException {
		writer.writeArrayFieldStart(SerializationKey.MENTORS);
		for(final Mentor mentor : mentors) {
			writer.writeStartObject();
			defaultUserSerializer.serialize(mentor, writer);
			writer.writeEndObject();
		}
		writer.writeEndArray();
	}
}