/*
 * @(#)DefaultJsonDeserializer<T>.java 1.0 17/04/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.utils.ApplicationContextHolder;
import br.gov.sp.fatec.mapskills.utils.JsonUtil;

/**
 * 
 * A classe {@link DefaultJsonDeserializer} implementa o
 * padrao de projetos template method, onde os passos em comum
 * para deserializacao de um objeto sao executados neste classe,
 * e delega a criacao do objeto para quem a extende.
 *
 * @author Marcelo
 * @version 1.0 17/04/2017
 */
public abstract class DefaultJsonDeserializer<T> extends JsonDeserializer<T> {
		
	protected final JsonUtil jsonUtil = ApplicationContextHolder.getBean("jsonUtil", JsonUtil.class);
	
	protected abstract T deserialize(final JsonNode node) throws IOException;
	
	@Override
	public T deserialize(final JsonParser jsonParser, final DeserializationContext arg1) throws IOException {
		
		final ObjectCodec oc = jsonParser.getCodec();
        final JsonNode node = oc.readTree(jsonParser);
        
		return deserialize(node);
		
	}
	
}
