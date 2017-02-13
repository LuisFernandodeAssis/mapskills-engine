/*
 * @(#)StudentSerializer.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved. Fatec Jessen Vidal
 * proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.user.User;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;

@Component
public class StudentSerializer extends DefaultUserSerializer {
	
	@Override
	public void serialize(final User user, final JsonGenerator generator) throws IOException {
		generator.writeStartObject();
		super.serializeDefaultValues(user, generator);
		final Student student = (Student) user;
		generator.writeStringField("ra", student.getRa());
		generator.writeStringField("institutionCode", student.getInstitutionCode());
		generator.writeStringField("courseCode", student.getCourseCode());
		generator.writeStringField("phone", student.getPhone());
		generator.writeBooleanField("isCompleted", student.isCompleted());
		generator.writeEndObject();
		
	}
	
}
