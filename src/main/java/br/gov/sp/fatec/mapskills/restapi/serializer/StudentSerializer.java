/*
 * @(#)StudentSerializer.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
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
	
	private final InstitutionSerializer institutionSerializer;
	private final CourseSerializer courseSerializer;
	
	@Override
	public void serialize(final Student student, final Enum<?> arg1, final JsonWriter writer) throws IOException {
		super.serialize(student, null, writer);
		writer.writeStringField(SerializationKey.RA, student.getFullRa());
		writer.writeStringField(SerializationKey.INSTITUTION_CODE, student.getInstitutionCode());
		writer.writeStringField(SerializationKey.COURSE_CODE, student.getCourseCode());
		writer.writeStringField(SerializationKey.PHONE, student.getPhone());
		writer.writeBooleanField(SerializationKey.COMPLETED, student.isCompleted());
		courseSerialize(student.getCourse(), writer);
		institutionSerialize(student.getInstitution(), writer);
	}
	
	private void courseSerialize(final Course course, final JsonWriter writer) throws IOException {
		writer.writeObjectFieldStart(SerializationKey.COURSE);
		courseSerializer.serialize(course, writer);
		writer.writeEndObject();		
	}

	private void institutionSerialize(final Institution institution, final JsonWriter writer) throws IOException {
		writer.writeObjectFieldStart(SerializationKey.INSTITUTION);
		institutionSerializer.serialize(institution, writer);
		writer.writeEndObject();
	}
}