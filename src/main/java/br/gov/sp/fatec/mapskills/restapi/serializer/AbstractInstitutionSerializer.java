/*
 * @(#)AbstractInstitutionSerializer<T>.java 1.0 07/05/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.institution.Institution;
import br.gov.sp.fatec.mapskills.utils.ApplicationContextHolder;
/**
 * 
 * A classe {@link AbstractInstitutionSerializer} contem
 * os metodos em comum para serializacao das informacoes de
 * uma instituicao.
 *
 * @author Marcelo
 * @version 1.0 07/05/2017
 */
public abstract class AbstractInstitutionSerializer<T> extends AbstractJsonSerializer<T> {

	private final DefaultInstitutionSerializer defaultSerializer;
	
	public AbstractInstitutionSerializer() {
		this.defaultSerializer = ApplicationContextHolder.getBean("defaultInstitutionSerializer", DefaultInstitutionSerializer.class);
	}
	
	public void serializeDefaultValues(final Institution institution, final JsonGenerator generator) throws IOException {
		defaultSerializer.serializeDefaultValues(institution, generator);
	}

}