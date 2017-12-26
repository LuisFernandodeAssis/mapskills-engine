/*
 * @(#)FileContextSerializer.java 1.0 1 19/12/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import br.gov.sp.fatec.mapskills.restapi.wrapper.FileContextWrapper;

/**
 * A classe {@link FileContextSerializer}
 *
 * @author Marcelo
 * @version 1.0 19/12/2017
 */
public class FileContextSerializer extends AbstractSerializer<FileContextWrapper> {

	@Override
	public void serialize(final FileContextWrapper wrapper,
			final Enum<?> arg1, final JsonWriter writer) throws IOException {
		
		writer.writeStartObject();
		writer.writeStringField(SerializationKey.BASE_64, wrapper.getBase64());
		writer.writeStringField(SerializationKey.FILENAME, wrapper.getFilename());
		writer.writeEndObject();
	}
}