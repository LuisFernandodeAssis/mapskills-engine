/*
 * @(#)StudentSerializerTest.java 1.0 17/04/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentWrapperTest;
/**
 * 
 * A classe {@link StudentSerializerTest} é utilizada 
 * para serializar um aluno nos casos de teste simulando
 * o JSON vindo do front end.
 *
 * @author Marcelo
 * @version 1.0 17/04/2017
 */
public class StudentSerializerTest extends JsonSerializer<StudentWrapperTest> {

	@Override
	public void serialize(final StudentWrapperTest wrapper, final JsonGenerator generator,
			final SerializerProvider arg2) throws IOException {

		final Student student = wrapper.getStudent();
		generator.writeStartObject();
		generator.writeStringField("name", student.getName());
		generator.writeStringField("phone", student.getPhone());
		generator.writeStringField("ra", student.getFullRa());
		generator.writeStringField("username", student.getUsername());
		generator.writeStringField("password", student.getPassword());
		generator.writeEndObject();
		
	}

}
