/*
 * @(#)StudentDetailsSerializer.java 1.0 11/02/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentDetailsWrapper;
import lombok.AllArgsConstructor;

/**
 * 
 * A classe {@link StudentDetailsSerializer} representa o aluno em
 * detalhes para que possa ser serializado pela aplicacao.
 *
 * @author Marcelo
 * @version 1.0 11/02/2017
 */
@Component
@AllArgsConstructor
public class StudentDetailsSerializer extends AbstractJsonSerializer<StudentDetailsWrapper> {
	
	private final CourseSerializer courseSerializer;
	private final StudentSerializer studentSerializer;

	@Override
	public void serialize(final StudentDetailsWrapper studentWrapper, final JsonGenerator generator) throws IOException {
		writeStartObject();
		studentSerialize(studentWrapper.getStudent());
		courseSerialize(studentWrapper.getCourse(), generator);
		institutionSerialize(studentWrapper.getInstitution());
		writeEndObject();
	}
	
	private void studentSerialize(final Student student) throws IOException {
		writeObjectFieldStart(SerializationKey.STUDENT);
		studentSerializer.serializeCore(student);
		writeEndObject();
	}

	private void courseSerialize(final Course course, final JsonGenerator generator) throws IOException {
		writeObjectFieldStart(SerializationKey.COURSE);
		courseSerializer.serialize(course, generator);
		writeEndObject();
	}
	
	private void institutionSerialize(final Institution institution) throws IOException {
		writeObjectFieldStart(SerializationKey.INSTITUTION);
		writeStringField(SerializationKey.CODE, institution.getCode());
		writeStringField(SerializationKey.COMPANY, institution.getCompany());
		writeNumberField(SerializationKey.CNPJ, institution.getCnpj());
		writeStringField(SerializationKey.CITY, institution.getCity());
		writeEndObject();
	}
}