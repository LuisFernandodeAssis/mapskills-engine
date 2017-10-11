/*
 * @(#)GameThemeListSerializer.java 1.0 24/12/2016
 *
 * Copyright (c) 2016, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import br.gov.sp.fatec.mapskills.domain.theme.GameTheme;
import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeListWrapper;

/**
 * 
 * A classe {@link GameThemeListSerializer} serializa uma lista de objetos
 * GameTheme, devolvendo todos temas criados pelo perfil administrador.
 *
 * @author Marcelo
 * @version 1.0 22/04/2017
 */
public class GameThemeListSerializer extends AbstractJsonSerializer<GameThemeListWrapper> {

	@Override
	public void serialize(final GameThemeListWrapper gameThemes, final JsonGenerator generator)	throws IOException {

		generator.writeStartArray();
		for(final GameTheme theme : gameThemes.getGameThemes()) {
			generator.writeStartObject();
			generator.writeNumberField("id", theme.getId());
			generator.writeStringField("name", theme.getName());
			generator.writeBooleanField("active", theme.isActive());
			generator.writeEndObject();
		}
		generator.writeEndArray();
		
	}

}
