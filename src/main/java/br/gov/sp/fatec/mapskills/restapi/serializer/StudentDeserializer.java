/*
 * @(#)StudentDeserializer.java 1.0 24/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentWrapper;
/**
 * 
 * A classe {@link StudentDeserializer} e responsavel
 * por deserializar um aluno para que seja cadastrado.
 *
 * @author Marcelo
 * @version 1.0 24/12/2016
 */
public class StudentDeserializer extends DefaultJsonDeserializer<StudentWrapper> {

	@Override
	protected StudentWrapper deserialize(final JsonNode node) throws IOException {
		
		final String ra = jsonUtil.getFieldTextValue(node, "ra");
        
        	try {
				return new StudentWrapper(new Student(
						ra,
						jsonUtil.getFieldTextValue(node, "name"),
						jsonUtil.getFieldTextValue(node, "phone"),
						jsonUtil.getFieldTextValue(node, "username"),
						jsonUtil.getFieldPassValue(node)));
			} catch (final MapSkillsException exception) {
				throw new IOException(exception);
			}
	}
}