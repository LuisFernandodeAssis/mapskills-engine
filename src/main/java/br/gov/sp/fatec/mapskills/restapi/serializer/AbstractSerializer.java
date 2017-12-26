/*
 * @(#)AbstractSerializer.java 1.0 1 18/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * A classe {@link AbstractSerializer}
 *
 * @author Marcelo
 * @version 1.0 18/11/2017
 */
public abstract class AbstractSerializer<T> extends JsonSerializer<T> {

	public void serialize(final T value, final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException {
		this.serialize(value, new JsonWriter(jsonGenerator));
	}

	public abstract void serialize(final T arg0, final Enum<?> arg1,
			final JsonWriter arg2) throws IOException;

	@SuppressWarnings("rawtypes")
	public void serialize(final T value, final JsonWriter writer) throws IOException {
		this.serialize(value, (Enum) null, (JsonWriter) writer);
	}
}