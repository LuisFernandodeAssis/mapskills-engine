/* @(#)StudentResultSerializer.java 1.0 04/01/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.List;

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
public class StudentResultSerializer extends AbstractSerializer<StudentResultWrapper> {
	
	@Override
	public void serialize(final StudentResultWrapper wrapper, final Enum<?> arg1, final JsonWriter writer) throws IOException {
		final StudentResult studentResult = wrapper.getObject();
		writer.writeStartObject();
		writer.writeNumberField(SerializationKey.ID, studentResult.getStudentId());
		writer.writeStringField(SerializationKey.RA, studentResult.getStudentRa());
		writer.writeStringField(SerializationKey.NAME, studentResult.getStudentName());
		writer.writeStringField(SerializationKey.COURSE_CODE, studentResult.getCourseCode());
		writer.writeStringField(SerializationKey.COURSE_NAME, studentResult.getCourseName());
		writer.writeStringField(SerializationKey.INSTITUTION_CODE, studentResult.getInstitutionCode());
		writer.writeStringField(SerializationKey.INSTITUTION_COMPANY, studentResult.getInstitutionCompany());
		writer.writeStringField(SerializationKey.INSTITUTION_LEVEL, studentResult.getInstitutionLevel());
		writer.writeNumberField(SerializationKey.START_YEAR, studentResult.getStartYear());
		writer.writeNumberField(SerializationKey.START_SEMESTER, studentResult.getStartSemester());
		serializeIndicators(studentResult.getStudentIndicators(), writer);
		writer.writeEndObject();
	}

	private void serializeIndicators(final List<StudentResultIndicator> studentsIndicator, final JsonWriter writer) throws IOException {
		writer.writeArrayFieldStart(SerializationKey.STUDENT_INDICATORS);
		for(final StudentResultIndicator indicator : studentsIndicator) {
			writer.writeStartObject();
			writer.writeStringField(SerializationKey.SKILL_NAME, indicator.getSkillName());
			writer.writeStringField(SerializationKey.SKILL_DESCRIPTION, indicator.getSkillDescription());
			writer.writeNumberField(SerializationKey.TOTAL, indicator.getTotal());
			writer.writeEndObject();
		}
		writer.writeEndArray();		
	}
}