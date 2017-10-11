/*
 * @(#)CourseListSerializer.java 1.0 14/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.restapi.wrapper.CourseListWrapper;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link CourseListSerializer} e responsavel
 * por serializar uma lista de cursos.
 *
 * @author Marcelo
 * @version 1.0 14/01/2017
 */
@Component
@AllArgsConstructor
public class CourseListSerializer extends AbstractJsonSerializer<CourseListWrapper> {

	private final CourseSerializer courseSerializer;
	
	@Override
	public void serialize(final CourseListWrapper courseList, final JsonGenerator generator) throws IOException {
		generator.writeStartArray();
		for(final Course course : courseList.getCourses()) {
			generator.writeStartObject();
			this.courseSerializer.serialize(course, generator);
			generator.writeEndObject();
		}
		generator.writeEndArray();
	}
	
}