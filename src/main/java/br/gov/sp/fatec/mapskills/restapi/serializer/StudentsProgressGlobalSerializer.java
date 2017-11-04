/*
 * @(#)StudentsProgressGlobalSerializer.java 1.0 01/03/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.report.entity.GlobalStudentsIndicator;
import br.gov.sp.fatec.mapskills.restapi.wrapper.StudentsProgressGlobalWrapper;
/**
 * 
 * A classe {@link StudentsProgressGlobalSerializer} e responavel
 * por serializar os resultados da porcentagem de alunos que finalizaram
 * e nao finalizaram o jogo de todos os cursos dos ensinos superiores
 * e tecnico.
 *
 * @author Marcelo
 * @version 1.0 01/03/2017
 */
public class StudentsProgressGlobalSerializer extends AbstractJsonSerializer<StudentsProgressGlobalWrapper> {

	@Override
	public void serialize(final StudentsProgressGlobalWrapper indicators, final JsonGenerator generator) throws IOException {
		generator.writeStartArray();
		for(final GlobalStudentsIndicator indicator : indicators.getIndicatorResults() ) {
			generator.writeStartObject();
			
			generator.writeStringField("level", indicator.isInstitutionLevelSuperior() ? "Fatecs" : "Etecs" );
			
			generator.writeArrayFieldStart("values");
			generator.writeNumber(ObjectUtils.isEmpty(indicator.getFinalized()) ? 0 : indicator.getFinalized());
			generator.writeNumber(ObjectUtils.isEmpty(indicator.getNotFinalized()) ? 0 : indicator.getNotFinalized());
			
			generator.writeEndArray();
			
			generator.writeEndObject();
		}		
		generator.writeEndArray();
	}
}