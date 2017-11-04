/*
 * @(#)StudentSerializer.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link StudentSerializer} e responsavel
 * por serializar o perfil de aluno com suas respectivas
 * informacoes.
 *
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
@Component
@AllArgsConstructor
public class StudentSerializer extends DefaultUserSerializer<Student> {
	
	private final DefaultInstitutionSerializer institutionSerializer;
	private final CourseSerializer courseSerializer;
	
	@Override
	public void serialize(final Student student, final JsonGenerator generator) throws IOException {
		setGenerator(generator);
		writeStartObject();
		serializeDefaultValues(student);
		serializeCore(student, generator);
		serializeInstitution(student.getInstitution(), generator);
		writeEndObject();		
	}
	
	public void serializeCore(final Student student, final JsonGenerator generator) throws IOException {
		writeStringField(SerializationKey.RA, student.getFullRa());
		writeStringField(SerializationKey.INSTITUTION_CODE, student.getInstitutionCode());
		writeStringField(SerializationKey.COURSE_CODE, student.getCourseCode());
		writeStringField(SerializationKey.PHONE, student.getPhone());
		writeBooleanField(SerializationKey.COMPLETED, student.isCompleted());
		writeObjectFieldStart(SerializationKey.COURSE);
		courseSerializer.serialize(student.getCourse(), generator);
		writeEndObject();
	}
	
	private void serializeInstitution(final Institution institution, final JsonGenerator generator) throws IOException {
		writeObjectFieldStart(SerializationKey.INSTITUTION);
		institutionSerializer.serialize(institution, generator);
		writeEndObject();
	}
}