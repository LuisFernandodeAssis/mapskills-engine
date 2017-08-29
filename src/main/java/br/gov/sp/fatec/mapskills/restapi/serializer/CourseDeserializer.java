/*
 * @(#)CourseDeserializer.java 1.0 15/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.domain.institution.Course;
import br.gov.sp.fatec.mapskills.domain.institution.CoursePeriod;
import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.domain.institution.InstitutionRepository;
import br.gov.sp.fatec.mapskills.restapi.wrapper.CourseWrapper;
import lombok.AllArgsConstructor;
/**
 * 
 * A classe {@link CourseDeserializer} é responsavel
 * por deserializar um <i>POST</i> de um curso para
 * que seja cadastrado ou atualizado.
 *
 * @author Marcelo
 * @version 1.0 15/01/2017
 */
@Component
@AllArgsConstructor
public class CourseDeserializer extends DefaultJsonDeserializer<CourseWrapper> {
	
	private final InstitutionRepository institutionRepository;

	@Override
	protected CourseWrapper deserialize(final JsonNode node) {
		return new CourseWrapper(new Course(jsonUtil.getFieldTextValue(node, "code"),
				jsonUtil.getFieldTextValue(node, "name"),
				CoursePeriod.valueOf(jsonUtil.getFieldTextValue(node, "period")),
				getInstitutionByCode(jsonUtil.getFieldTextValue(node, "institutionCode"))));
	}
	
	private Institution getInstitutionByCode(final String code) {
		return institutionRepository.findByCode(code);
	}

}
