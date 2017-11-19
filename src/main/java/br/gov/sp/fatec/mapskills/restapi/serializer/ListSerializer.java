/*
 * @(#)ListSerializer.java 1.0 1 18/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.restapi.wrapper.ListWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link ListSerializer}
 *
 * @author Marcelo
 * @version 1.0 18/11/2017
 */
@Component
@AllArgsConstructor
public class ListSerializer<T> extends AbstractSerializer<ListWrapper<T>> {
	
	@SuppressWarnings("rawtypes")
	private final Map<Class, AbstractSerializer> domainSerializers;
	
	@SuppressWarnings("unchecked")
	@Override
	public void serialize(final ListWrapper<T> wrapper, final Enum<?> arg1,
			final JsonWriter writer) throws IOException {
		
		writer.writeStartArray();
		for(final T item : wrapper.getList()) {
			writer.writeStartObject();
			domainSerializers.get(item.getClass()).serialize(item, writer);
			writer.writeEndObject();
		}
		writer.writeEndArray();
	}
}