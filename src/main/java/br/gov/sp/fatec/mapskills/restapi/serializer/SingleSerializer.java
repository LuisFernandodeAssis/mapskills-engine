/*
 * @(#)SingleSerializer.java 1.0 1 18/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.gov.sp.fatec.mapskills.restapi.wrapper.SingleWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link SingleSerializer}
 *
 * @author Marcelo
 * @version 1.0 20/09/2017
 */
@Component
@AllArgsConstructor
public class SingleSerializer<T> extends AbstractSerializer<SingleWrapper<T>> {
	
	@SuppressWarnings("rawtypes")
	private final Map<Class, AbstractSerializer> domainSerializers;

	@Override
	@SuppressWarnings("unchecked")
	public void serialize(final SingleWrapper<T> value, final Enum<?> arg1,
			final JsonWriter writer) throws IOException {
		
		final T object = value.getObject();
		writer.writeStartObject();
		domainSerializers.get(object.getClass()).serialize(object, null, writer);
		writer.writeEndObject();
	}
}