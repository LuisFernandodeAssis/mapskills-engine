/*
 * @(#)GameThemeListSerializer.java 1.0 24/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;

/**
 * 
 * A classe {@link GameThemeSerializer} serializa uma lista de objetos
 * GameTheme, devolvendo todos temas criados pelo perfil administrador.
 *
 * @author Marcelo
 * @version 1.0 22/04/2017
 */
@Component
public class GameThemeSerializer extends AbstractSerializer<GameTheme> {
	
	@Override
	public void serialize(final GameTheme theme, final Enum<?> arg1, final JsonWriter writer) throws IOException {
		writer.writeNumberField(SerializationKey.ID, theme.getId());
		writer.writeStringField(SerializationKey.NAME, theme.getName());
		writer.writeBooleanField(SerializationKey.ACTIVE, theme.isActive());		
	}
}