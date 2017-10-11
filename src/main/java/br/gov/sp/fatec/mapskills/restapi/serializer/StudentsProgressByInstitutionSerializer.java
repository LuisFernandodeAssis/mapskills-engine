/*
 * @(#)StudentsProgressByInstitutionSerializer.java 1.0 01/03/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressByInstitutionWrapper;
/**
 * 
 * A classe {@link StudentsProgressByInstitutionSerializer} e responsavel
 * por serializar os resultados da quantidade de alunos que finalizaram e
 * nao finalizaram de todos os cursos de uma instituicao.
 *
 * @author Marcelo
 * @version 1.0 01/03/2017
 */
public class StudentsProgressByInstitutionSerializer extends AbstractJsonSerializer<StudentsProgressByInstitutionWrapper> {

	@Override
	public void serialize(final StudentsProgressByInstitutionWrapper progress, final JsonGenerator generator) throws IOException {
		
		generator.writeStartArray();
		
		for(final Object[] tuple : progress.getResultSet()) {
			generator.writeStartObject();
			
			generator.writeStringField("course", String.valueOf(tuple[4]));
			
			generator.writeArrayFieldStart("values");
			generator.writeNumber(Integer.valueOf(tuple[5] != null ? tuple[5].toString() : "0"));
			generator.writeNumber(Integer.valueOf(tuple[6] != null ? tuple[6].toString() : "0"));
			generator.writeEndArray();
			
			generator.writeEndObject();
		}
		
		generator.writeEndArray();
		
	}

}
