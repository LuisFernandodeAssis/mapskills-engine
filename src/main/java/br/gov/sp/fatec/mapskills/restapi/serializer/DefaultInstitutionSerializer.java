/*
 * @(#)DefaultInstitutionSerializer.java 1.0 07/02/2017
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
/**
 * 
 * A classe {@link DefaultInstitutionSerializer} e responsavel
 * por serializar os atibutos basicos de uma instituicao.
 *
 * @author Marcelo
 * @version 1.0 07/02/2017
 */
@Component
public class DefaultInstitutionSerializer {
	
	protected void serialize(final Institution institution, final JsonGenerator generator) throws IOException {
		generator.writeNumberField("id", institution.getId());
		generator.writeStringField("code", institution.getCode());
		generator.writeNumberField("cnpj", institution.getCnpj());
		generator.writeStringField("company", institution.getCompany());
		generator.writeStringField("level", institution.getLevel().name());
		generator.writeStringField("city", institution.getCity());
	}
}