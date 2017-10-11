/*
 * @(#)CourseSerializer.java 1.0 1 23/08/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Course;

/**
 * A classe {@link CourseSerializer} e responsavel
 * por serializar objeto curso.
 *
 * @author Marcelo
 * @version 1.0 23/08/2017
 */
@Component
public class CourseSerializer extends AbstractJsonSerializer<Course> {
	
	@Override
	public void serialize(final Course course, final JsonGenerator generator) throws IOException {
		writeNumberField(SerializationKey.ID, course.getId());
		writeStringField(SerializationKey.CODE, course.getCode());
		writeStringField(SerializationKey.NAME, course.getName());
		writeStringField(SerializationKey.PERIOD, course.getNamePeriod());
		writeStringField(SerializationKey.INSTITUTION_CODE, course.getInstitutionCode());
	}
}