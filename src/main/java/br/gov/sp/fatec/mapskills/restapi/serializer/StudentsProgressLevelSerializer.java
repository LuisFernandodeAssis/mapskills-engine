/*
 * @(#)StudentsProgressLevelSerializer.java 1.0 01/03/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressLevelWrapper;
/**
 * 
 * A classe {@link StudentsProgressLevelSerializer} e responsavel
 * por serializar os resutados contidos na classe
 * <code>StudentsProgressLevelWrapper</code>. JSON utilizado
 * na tela de perfil administrador.
 *
 * @author Marcelo
 * @version 1.0 01/03/2017
 */
public class StudentsProgressLevelSerializer extends DefaultJsonSerializer<StudentsProgressLevelWrapper> {

	@Override
	public void serialize(final StudentsProgressLevelWrapper progress, final JsonGenerator generator) throws IOException {

		generator.writeStartObject();
		this.dataGenerate(progress.getResultSet(), generator);
		this.institutionGenerate(progress.getResultSet(), generator);
		generator.writeEndObject();
	}
	
	private void dataGenerate(final List<Object[]> resultSet, final JsonGenerator generator) throws IOException {		
		generator.writeArrayFieldStart("data");
		generator.writeStartArray();
		for(final Object[] tuple : resultSet) {
			final int totalStudents = tuple[6] == null ? 0 : Integer.valueOf(tuple[6].toString());
			final double totalFinalized = tuple[5] == null ? 0 : Double.valueOf(tuple[5].toString());
			generator.writeNumber(this.calcPercentage(totalFinalized, totalStudents));
		}
		generator.writeEndArray();
		generator.writeStartArray();
		for(final Object[] tuple : resultSet) {
			final int totalStudents = tuple[6] == null ? 0 : Integer.valueOf(tuple[6].toString());
			final double totalNotFinalized = tuple[4] == null ? 0 : Double.valueOf(tuple[4].toString());
			generator.writeNumber(this.calcPercentage(totalNotFinalized, totalStudents));
		}
		generator.writeEndArray();
		generator.writeEndArray();
	}
	
	private void institutionGenerate(final List<Object[]> resultSet, final JsonGenerator generator) throws IOException {
		generator.writeArrayFieldStart("institutions");
		for(final Object[] tuple : resultSet) {
			generator.writeStartObject();
			generator.writeStringField("code", tuple[1].toString());
			generator.writeStringField("company", tuple[3].toString());
			generator.writeEndObject();
		}
		generator.writeEndArray();
	}
	/**
	 * Metodo que calcula uma porcentagem a partir de uma quantidade
	 * e um total.
	 */
	private double calcPercentage(final double quantity, final double total) {
		return (quantity/total) * 100;
	}

}
