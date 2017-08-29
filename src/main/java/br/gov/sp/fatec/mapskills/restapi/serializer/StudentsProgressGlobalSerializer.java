/*
 * @(#)StudentsProgressGlobalSerializer.java 1.0 01/03/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.InstitutionLevel;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressGlobalWrapper;
/**
 * 
 * A classe {@link StudentsProgressGlobalSerializer} e responavel
 * por serializar os resultados da porcentagem de alunos que finalizaram
 * e nao finalizaram o jogo de todos os curos dos ensinos superiores
 * e tecnico.
 *
 * @author Marcelo
 * @version 1.0 01/03/2017
 */
public class StudentsProgressGlobalSerializer extends DefaultJsonSerializer<StudentsProgressGlobalWrapper> {

	@Override
	public void serialize(final StudentsProgressGlobalWrapper progess, final JsonGenerator generator) throws IOException {
		
		generator.writeStartArray();
		
		for(final Object[] tuple : progess.getResultSet() ) {
			generator.writeStartObject();
			
			final InstitutionLevel level = InstitutionLevel.valueOf(String.valueOf(tuple[1]));
			generator.writeStringField("level", level.isSuperior() ? "Fatecs" : "Etecs" );
			
			generator.writeArrayFieldStart("values");
			generator.writeNumber(tuple[3] != null ? Integer.valueOf(tuple[3].toString()) : 0);
			generator.writeNumber(tuple[2] != null ? Integer.valueOf(tuple[2].toString()) : 0);
			generator.writeEndArray();
			
			generator.writeEndObject();
		}
		
		generator.writeEndArray();
		
	}

}
