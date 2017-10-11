/* @(#)InstitutionListSerializer.java 1.0 08/01/2017
 *
 * Copyright (c) 2016, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.restapi.wrapper.InstitutionListWrapper;
/**
 * 
 * A classe {@link InstitutionListSerializer} e responsavel
 * por serializar uma lista de instituicao.
 *
 * @author Marcelo
 * @version 1.0 08/01/2017
 */
public class InstitutionListSerializer extends AbstractInstitutionSerializer<InstitutionListWrapper> {
	
	@Override
	public void serialize(final InstitutionListWrapper listWrapper, final JsonGenerator generator) throws IOException {
		generator.writeStartArray();
		for(final Institution institution : listWrapper.getInstitutions()) {
			serializeDefaultValues(institution, generator);
		}
		generator.writeEndArray();
	}
}