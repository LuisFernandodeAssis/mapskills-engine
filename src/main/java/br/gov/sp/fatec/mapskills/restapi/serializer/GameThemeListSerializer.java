package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.gov.sp.fatec.mapskills.restapi.wrapper.GameThemeListWrapper;

public class GameThemeListSerializer extends JsonSerializer<GameThemeListWrapper> {

	@Override
	public void serialize(final GameThemeListWrapper arg0, final JsonGenerator arg1, final SerializerProvider arg2)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

}