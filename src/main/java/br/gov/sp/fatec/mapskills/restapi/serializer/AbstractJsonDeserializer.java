/*
 * @(#)DefaultJsonDeserializer<T>.java 1.0 17/04/2017
 *
 * Copyright (c) 2017, Fatec Jessen Vidal. All rights reserved.
 * Fatec Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */
package br.gov.sp.fatec.mapskills.restapi.serializer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * A classe {@link AbstractJsonDeserializer} implementa o
 * padrao de projetos template method, onde os passos em comum
 * para deserializacao de um objeto sao executados neste classe,
 * e delega a criacao do objeto para quem a extende.
 *
 * @author Marcelo
 * @version 1.0 17/04/2017
 */
public abstract class AbstractJsonDeserializer<T> extends JsonDeserializer<T> {
	
	@Autowired
	private PasswordEncoder encoder;
	
	protected abstract T deserialize(final JsonNode node) throws IOException;
	
	@Override
	public T deserialize(final JsonParser jsonParser, final DeserializationContext arg1) throws IOException {
		final ObjectCodec oc = jsonParser.getCodec();
        final JsonNode node = oc.readTree(jsonParser);        
		return deserialize(node);
	}
	
	public String getFieldTextValue(final JsonNode node, final Enum<?> fieldName) {
		final String field = fieldName.toString();
		return !ObjectUtils.isEmpty(node) && node.has(field) ? node.get(field).textValue() : null;
	}

	public Long getFieldLongValue(final JsonNode node, final Enum<?> fieldName) {
		final String field = fieldName.toString();
		return !ObjectUtils.isEmpty(node) && node.has(field) ? node.get(field).asLong() : null;
	}

	public Integer getFieldIntegerValue(final JsonNode node, final Enum<?> fieldName) {
		final String field = fieldName.toString();
		return !ObjectUtils.isEmpty(node) && node.hasNonNull(field) ? node.get(field).asInt() : null;
	}

	public Boolean hasNonNull(final JsonNode node, final Enum<?> fieldName) {
		return ObjectUtils.isEmpty(node) ? false : node.hasNonNull(fieldName.toString());
	}

	public Boolean has(final JsonNode node, final Enum<?> fieldName) {
		return ObjectUtils.isEmpty(node) ? false : node.has(fieldName.toString());
	}
	
	public Boolean isNull(final JsonNode node) {
		return node == null || node.isNull();
	}

	public JsonNode get(final JsonNode node, final Enum<?> fieldName) {
		return ObjectUtils.isEmpty(node) ? null : node.get(fieldName.toString());
	}

	/**
	 * Metodo que devolve um password codificado caso haja.
	 * se nao devolve um password codificado padrao.
	 */
	public String getFieldPassValue(final JsonNode node) {
		final String pass = getFieldTextValue(node, SerializationKey.PASS);
		return StringUtils.isEmpty(pass) ? getEncryptedPass() : encoder.encode(pass);
	}
	/**
	 * metodo que define a senha padrao de acesso a todos perfis.
	 */
	private String getEncryptedPass() {
		return encoder.encode("mudar123");
	}	
}