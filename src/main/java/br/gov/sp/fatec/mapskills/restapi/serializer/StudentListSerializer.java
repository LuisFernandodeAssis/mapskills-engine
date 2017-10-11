/*
 * @(#)StudentListSerializer.java 1.0 24/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentPageWrapper;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link StudentListSerializer} e responsavel
 * por serializar uma lista de alunos.
 *
 * @author Marcelo
 * @version 1.0 24/12/2016
 */
@Component
@AllArgsConstructor
public class StudentListSerializer extends AbstractJsonSerializer<StudentPageWrapper> {
	
	private final CourseSerializer courseSerializer;
	private final StudentSerializer studentSerializer;

	@Override
	public void serialize(final StudentPageWrapper studentListWrapper, final JsonGenerator generator) throws IOException {
		
		writeStartArray();
		for(final Student student : studentListWrapper.getStudents()) {
			writeStartObject();
			studentSerializer.serializeCore(student);
			courseSerialize(student.getCourse(), generator);
			writeEndObject();
		}
		writeEndArray();
	}	
	
	private void courseSerialize(final Course course, final JsonGenerator generator) throws IOException {
		writeObjectFieldStart(SerializationKey.COURSE);
		courseSerializer.serialize(course, generator);
		writeEndObject();
	}	
}