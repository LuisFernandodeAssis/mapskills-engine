/* @(#)StudentResultSerializer.java 1.0 04/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentResultWrapper;
import br.gov.sp.fatec.mapskills.studentresult.StudentResult;
import br.gov.sp.fatec.mapskills.studentresult.StudentResultIndicator;
/**
 * 
 * A classe {@link StudentResultSerializer} responsavel
 * por serializar as informacoes para o servico de integracao.
 *
 * @author Marcelo
 * @version 1.0 04/01/2017
 */
public class StudentResultSerializer extends AbstractJsonSerializer<StudentResultWrapper> {

	@Override
	public void serialize(final StudentResultWrapper wrapper,
			final JsonGenerator gen) throws IOException {
		final StudentResult studentResult = wrapper.getStudentResult();
		writeStartObject();
		writeNumberField(SerializationKey.ID, studentResult.getStudentId());
		writeStringField(SerializationKey.RA, studentResult.getStudentRA());
		writeStringField(SerializationKey.NAME, studentResult.getStudentName());
		gen.writeStringField("courseCode", studentResult.getCourseCode());
		gen.writeStringField("courseName", studentResult.getCourseName());
		writeStringField(SerializationKey.INSTITUTION_CODE, studentResult.getInstitutionCode());
		gen.writeStringField("institutionCompany", studentResult.getInstitutionCompany());
		writeStringField(SerializationKey.INSTITUTION_LEVEL, studentResult.getInstitutionLevel());
		gen.writeNumberField("startYear", studentResult.getStartYear());
		gen.writeNumberField("startSemester", studentResult.getStartSemester());
		serializeIndicators(studentResult.getStudentIndicators(), gen);
		writeEndObject();
	}

	private void serializeIndicators(final List<StudentResultIndicator> studentsIndicator,
			final JsonGenerator gen) throws IOException {
		gen.writeArrayFieldStart("studentIndicators");
		for(final StudentResultIndicator indicator : studentsIndicator) {
			gen.writeStringField("skillName", indicator.getSkillName());
			gen.writeStringField("skillDescription", indicator.getSkillDescription());
			gen.writeNumberField("total", indicator.getTotal());
		}
		gen.writeEndArray();		
	}
}