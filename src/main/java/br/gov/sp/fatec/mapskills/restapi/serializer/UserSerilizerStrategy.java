/*
 * @(#)UserSerilizerStrategy.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.user.User;
/**
 * 
 * A interface {@link UserSerilizerStrategy} representa
 * a abstracao de serializacao dos perfis da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
@FunctionalInterface
public interface UserSerilizerStrategy<T extends User> {
	
	public void serialize(final T user, final JsonGenerator generator) throws IOException;

}