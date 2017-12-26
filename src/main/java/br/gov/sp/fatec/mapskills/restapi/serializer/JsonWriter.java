/*
 * @(#)JsonWriter.java 1.0 1 18/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * A classe {@link JsonWriter}
 *
 * @author Marcelo
 * @version 1.0 18/11/2017
 */
public class JsonWriter {

	private final JsonGenerator generator;

	public JsonWriter(final JsonGenerator generator) {
		this.generator = generator;
	}
	
	protected void writeNumberField(final Enum<?> key, final Long value) throws IOException {
		if(ObjectUtils.isEmpty(value)) {
			generator.writeNullField(key.toString());
			return;
		}
		generator.writeNumberField(key.toString(), value);
	}
	
	protected void writeNumberField(final Enum<?> key, final Integer value) throws IOException {
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

	public void writeNullField(final Enum<?> key) throws IOException {
		generator.writeNullField(key.toString());
	}
}