/*
 * @(#)PageSerializer.java 1.0 1 18/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.restapi.wrapper.PageWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link PageSerializer}
 *
 * @author Marcelo
 * @version 1.0 18/11/2017
 */
@Component
@AllArgsConstructor
public class PageSerializer<T> extends AbstractSerializer<PageWrapper<T>> {
	
	@SuppressWarnings("rawtypes")
	private final Map<Class, AbstractSerializer> domainSerializers;

	@Override
	@SuppressWarnings("unchecked")
	public void serialize(final PageWrapper<T> value, final Enum<?> arg1,
			final JsonWriter writer) throws IOException {
		
		writer.writeStartObject();
		writer.writeNumberField(SerializationKey.REMAINING_PAGES, value.getRemaningPages());
		writer.writeNumberField(SerializationKey.TOTAL_ELEMENTS, value.getTotalElements());
		writer.writeArrayFieldStart(SerializationKey.CONTENT);
		for(final T item : value.getList()) {
			writer.writeStartObject();
			domainSerializers.get(item.getClass()).serialize(item, writer);
			writer.writeEndObject();
		}
		writer.writeEndArray();
		writer.writeEndObject();
	}
}