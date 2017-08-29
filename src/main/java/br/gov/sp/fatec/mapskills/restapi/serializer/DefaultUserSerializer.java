/*
 * @(#)DefaultUserSerializer.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.user.User;
/**
 * 
 * A classe {@link DefaultUserSerializer} e responsavel
 * por serializar os atributos basicos de um perfil da
 * aplicacao.
 *
 * @author Marcelo
 * @version 1.0 10/11/2016
 */
@Component
public class DefaultUserSerializer<T extends User> implements UserSerilizerStrategy<T> {
	
	protected void serializeDefaultValues(final T user, final JsonGenerator generator) throws IOException {
		generator.writeNumberField("id", user.getId());
		generator.writeStringField("name", user.getName());
		generator.writeStringField("profile", user.getProfile().name());
	}

	@Override
	public void serialize(final T user, final JsonGenerator generator) throws IOException {
		generator.writeStartObject();
		serializeDefaultValues(user, generator);
		generator.writeEndObject();
	}

}