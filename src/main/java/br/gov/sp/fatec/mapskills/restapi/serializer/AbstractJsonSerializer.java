/*
 * @(#)DefaultJsonSerializer<T>.java 1.0 07/05/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.Setter;

/**
 * 
 * A classe {@link AbstractJsonSerializer} define metodos
 * em comum entre todos serializers da aplicacao.
 *
 * @author Marcelo
 * @version 1.0 07/05/2017
 */
public abstract class AbstractJsonSerializer<T> extends JsonSerializer<T> {
	
	@Setter
	private JsonGenerator generator;
	
	protected abstract void serialize(final T object, final JsonGenerator generator) throws IOException;

	@Override
	public void serialize(final T wrapper, final JsonGenerator generator,
			final SerializerProvider arg2) throws IOException {
		this.generator = generator;
		this.serialize(wrapper, generator);
	}
	
	protected void writeNumberField(final Enum<?> key, final Long value) throws IOException {
		if(ObjectUtils.isEmpty(value)) {
			generator.writeNullField(key.toString());
			return;
		}
		generator.writeNumberField(key.toString(), value);
	}
	
	protected void writeNumber(final Integer value) throws IOException {
		generator.writeNumber(value);
	}
	
	protected void writeStringField(final Enum<?> key, final String value) throws IOException {
		generator.writeStringField(key.toString(), value);
	}
	
	protected void writeStringField(final Enum<?> key, final Enum<?> value) throws IOException {
		generator.writeStringField(key.toString(), value.name());
	}
	
	protected void writeBooleanField(final Enum<?> key, final Boolean value) throws IOException {
		generator.writeBooleanField(key.toString(), value);
	}
	
	protected void writeObjectFieldStart(final Enum<?> key) throws IOException {
		generator.writeObjectFieldStart(key.toString());
	}
	
	protected void writeStartArray() throws IOException {
		generator.writeStartArray();
	}
	
	protected void writeEndArray() throws IOException {
		generator.writeEndArray();
	}
	
	protected void writeStartObject() throws IOException {
		generator.writeStartObject();
	}
	
	protected void writeEndObject() throws IOException {
		generator.writeEndObject();
	}
	
	protected void writeArrayFieldStart(final Enum<?> key) throws IOException {
		generator.writeArrayFieldStart(key.toString());
	}
}