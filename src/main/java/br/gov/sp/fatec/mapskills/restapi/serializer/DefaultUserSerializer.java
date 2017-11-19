/*
 * @(#)DefaultUserSerializer.java 1.0 10/11/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

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
public class DefaultUserSerializer<T extends User> extends AbstractSerializer<T> {
	
	@Override
	public void serialize(final T user, final Enum<?> arg1, final JsonWriter writer) throws IOException {
		writer.writeNumberField(SerializationKey.ID, user.getId());
		writer.writeStringField(SerializationKey.NAME, user.getName());
		writer.writeStringField(SerializationKey.PROFILE, user.getProfile());
		writer.writeStringField(SerializationKey.USERNAME, user.getUsername());
		writer.writeStringField(SerializationKey.PASS, SerializationKey.EMPTY_PASS);		
	}	
}