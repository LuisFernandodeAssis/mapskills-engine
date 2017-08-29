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

import br.gov.sp.fatec.mapskills.domain.user.student.Student;
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
public class StudentSerializer extends DefaultUserSerializer<Student> {
	
	@Override
	public void serialize(final Student student, final JsonGenerator generator) throws IOException {
		generator.writeStartObject();
		super.serializeDefaultValues(student, generator);
		generator.writeStringField("ra", student.getRa());
		generator.writeStringField("institutionCode", student.getInstitutionCode());
		generator.writeStringField("courseCode", student.getCourseCode());
		generator.writeStringField("phone", student.getPhone());
		generator.writeBooleanField("isCompleted", student.isCompleted());
		generator.writeEndObject();
		
	}
	
}