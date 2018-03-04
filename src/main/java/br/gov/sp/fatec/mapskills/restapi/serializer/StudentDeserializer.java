/*
 * @(#)StudentDeserializer.java 1.0 24/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.MapSkillsException;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionRepository;
import br.gov.sp.fatec.mapskills.domain.user.student.AcademicRegistry;
import br.gov.sp.fatec.mapskills.domain.user.student.Student;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link StudentDeserializer} e responsavel
 * por deserializar um aluno para que seja cadastrado.
 *
 * @author Marcelo
 * @version 1.0 24/12/2016
 */
@Component
@AllArgsConstructor
public class StudentDeserializer extends AbstractJsonDeserializer<StudentWrapper> {

	private final InstitutionRepository institutionRepository;

	@Override
	protected StudentWrapper deserialize(final JsonNode node) throws IOException {
		final AcademicRegistry registry = new AcademicRegistry(getFieldTextValue(node, SerializationKey.RA));
		final Institution institution = institutionRepository.findByCode(registry.getInstitutionCode());

		try {
			return new StudentWrapper(
					new Student(getFieldTextValue(node, SerializationKey.RA),
							getFieldTextValue(node, SerializationKey.NAME),
							getFieldTextValue(node, SerializationKey.PHONE),
							institution.getCourseByCode(registry.getCourseCode()),
							getFieldTextValue(node, SerializationKey.USERNAME),
							getFieldPassValue(node)));
		} catch (final MapSkillsException exception) {
			throw new IOException(exception);
		}
	}
}